package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * The {@link DiscoveryApplication} is a cloud-native Spring Boot application for providing
 * service discovery using Netflix Eureka.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@EnableEurekaServer
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceHealthIndicatorAutoConfiguration.class)
public class DiscoveryApplication {

    public static void main(String... args) {
        SpringApplication.run(DiscoveryApplication.class, args);
    }

}