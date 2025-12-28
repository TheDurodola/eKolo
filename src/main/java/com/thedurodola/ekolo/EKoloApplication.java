package com.thedurodola.ekolo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EKoloApplication {

    public static void main(String[] args) {
        SpringApplication.run(EKoloApplication.class, args);
    }

}
