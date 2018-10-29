package demo;

import demo.domain.Catalog;
import demo.domain.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The {@link InventoryApplication} is a cloud-native Spring Boot application that manages
 * a bounded context for @{link Address}, @{link Catalog}, @{link Inventory}, @{link LineItem}
 * , @{link Product}, @{link Shipment}, @{link ShoppingCart} and @{link Warehouse}.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@SpringBootApplication(exclude = DataSourceHealthIndicatorAutoConfiguration.class)
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableEurekaClient
@EnableHystrix
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Configuration
    protected static class RepositoryMvcConfiguration extends RepositoryRestConfigurerAdapter {
        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(Catalog.class, Product.class);
        }
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

    @Configuration
    public class SecurityActuatorConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/actuator/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().antMatchers("/**").permitAll()
                    .and()
                    .csrf().disable();
        }

    }

}