package demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The {@link UserApplication} is a cloud-native Spring Boot application that manages
 * a bounded context for @{link User}, @{link Account}, @{link CreditCard}, and @{link Address}
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@SpringBootApplication(exclude = DataSourceHealthIndicatorAutoConfiguration.class)
@EnableJpaAuditing
@EnableJpaRepositories
//@EnableEurekaClient
@EnableHystrix
@EnableTransactionManagement
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(DatabaseInitializer databaseInitializer) {
        return args -> {
            // Initialize the database for end to end integration testing
            databaseInitializer.populate();
        };
    }

    @Component
    public static class CustomizedRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.setBasePath("/api");
        }
    }
}
