package bogen.studio.passengers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.TimeZone;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableMongoAuditing
@Configuration
public class PassengersApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Iran"));
        SpringApplication.run(PassengersApplication.class, args);
    }

}
