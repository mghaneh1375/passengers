package bogen.studio.passengers;

import bogen.studio.passengers.Components.JobHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.TimeZone;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableMongoAuditing
@Configuration
public class PassengersApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Iran"));
        ApplicationContext applicationContext = SpringApplication.run(PassengersApplication.class, args);
        JobHandler jobHandler = applicationContext.getBean(JobHandler.class);
        jobHandler.run();
    }

}
