package br.com.davimonteiro;

import akka.actor.ActorSystem;
import io.beethoven.Beethoven;
import io.beethoven.config.EnableBeethoven;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@EnableBeethoven
@SpringBootApplication
public class BeethovenApplication {

    public static void main(String... args) throws Exception {
        ApplicationContext context = SpringApplication.run(BeethovenApplication.class, args);
        ActorSystem actorSystem = context.getBean(ActorSystem.class);
        Beethoven.initialize(actorSystem);
    }

}
