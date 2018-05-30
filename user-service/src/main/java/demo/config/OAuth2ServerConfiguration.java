package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "br.gov.ce.sda.ateratividades";
    private static final String CLIENT_ID = "acme";
    private static final String CLIENT_SECRET = "acmesecret";
    public static final String SCOPE = "openid";
    public static final String[] GRANT_TYPES = {"authorization_code", "refresh_token", "password"};

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private OAuthLogoutSuccessHandler logoutSuccessHandler;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/resources/**", "/login").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/oauth/authorize").authenticated();
        }

        @Bean
        HttpSessionSecurityContextRepository contextRepository() {
            return new HttpSessionSecurityContextRepository();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient(CLIENT_ID)
                    .secret(CLIENT_SECRET)
                    .authorizedGrantTypes(GRANT_TYPES)
                    .scopes(SCOPE)
                    .autoApprove(true)
                    .resourceIds(RESOURCE_ID);
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(tokenStore());
            return tokenServices;
        }
    }

}
