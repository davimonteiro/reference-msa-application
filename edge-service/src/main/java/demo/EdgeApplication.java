package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * The {@link EdgeApplication} is a cloud-native Spring Boot application that
 * provides an approach to reverse proxy creation using routers and filters.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@SpringBootApplication(exclude = DataSourceHealthIndicatorAutoConfiguration.class)
@EnableEurekaClient
@EnableZuulProxy
@EnableResourceServer
@EnableHystrix
public class EdgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeApplication.class, args);
    }

    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/actuator/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
        }
    }
}