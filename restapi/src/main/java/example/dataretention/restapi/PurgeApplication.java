package example.dataretention.restapi;

import example.dataretention.restapi.config.PurgeDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"example.dataretention.restapi", "example.dataretention.shared"})
@EnableConfigurationProperties({PurgeDetails.class})
@EnableScheduling
public class PurgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurgeApplication.class, args);
    }

}
