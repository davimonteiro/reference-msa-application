package demo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The {@link AdminApplication} is responsible for administration of spring boot applications.
 *
 * @author Davi Monteiro
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceHealthIndicatorAutoConfiguration.class)
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
