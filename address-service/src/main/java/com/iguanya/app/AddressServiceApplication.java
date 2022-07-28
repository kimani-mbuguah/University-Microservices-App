package com.iguanya.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.iguanya.controller", "com.iguanya.service"})
@EntityScan("com.iguanya.entity")
@EnableJpaRepositories("com.iguanya.repository")
public class AddressServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressServiceApplication.class, args);
    }

}
