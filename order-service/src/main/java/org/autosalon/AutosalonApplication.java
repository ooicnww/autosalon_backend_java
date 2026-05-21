package org.autosalon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutosalonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutosalonApplication.class, args);
    }

}