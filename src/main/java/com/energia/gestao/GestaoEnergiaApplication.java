package com.energia.gestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GestaoEnergiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoEnergiaApplication.class, args);
    }

    // Bean para consumir APIs RESTful
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}