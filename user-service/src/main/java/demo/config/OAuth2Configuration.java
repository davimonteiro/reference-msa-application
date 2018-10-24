package demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
public class AuthorizationServerConfig  {


    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

        public static final String CLIENT_ID = "acme";
        public static final String CLIENT_SECRET = "acmesecret";
        public static final String SCOPE = "openid";
        public static final String[] GRANT_TYPES = {"authorization_code", "refresh_token", "password"};

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient(CLIENT_ID)
                    .secret(CLIENT_SECRET)
                    .authorizedGrantTypes(GRANT_TYPES)
                    .scopes(SCOPE);
                    //.autoApprove(true);
        }
    }
}
