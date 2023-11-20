package com.bilgeadam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")

public class ApiGatewayServiceApplication {
    public static void main(String[] args) {
     SpringApplication.run(ApiGatewayServiceApplication.class);
    }
}
