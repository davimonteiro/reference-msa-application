package io.beethoven;

import akka.actor.ActorSystem;
import io.beethoven.config.EnableBeethoven;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The {@link BeethovenApplication} is a lightweight platform for microservice orchestration.
 *
 * @author Davi Monteiro
 */
@EnableEurekaClient
@EnableBeethoven
@SpringBootApplication(exclude = DataSourceHealthIndicatorAutoConfiguration.class)
public class BeethovenApplication {

    public static void main(String... args) {
        ApplicationContext context = SpringApplication.run(BeethovenApplication.class, args);
        ActorSystem actorSystem = context.getBean(ActorSystem.class);
        Beethoven.initialize(actorSystem);
    }

    @Configuration
    public class SecurityActuatorConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/**");
        }

    }

}