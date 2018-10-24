package br.com.davimonteiro;

import akka.actor.ActorSystem;
import io.beethoven.Beethoven;
import io.beethoven.config.EnableBeethoven;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableEurekaClient
@EnableBeethoven
@SpringBootApplication
public class BeethovenApplication {

    public static void main(String... args) throws Exception {
        ApplicationContext context = SpringApplication.run(BeethovenApplication.class, args);
        ActorSystem actorSystem = context.getBean(ActorSystem.class);
        Beethoven.initialize(actorSystem);
    }

    @Configuration
    public class SecurityActuatorConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/actuator/**");
        }

    }

}
