package com.example.demo.configs;

import com.example.demo.exceptions.ExceptionCount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MyConfig {

    @Bean
    public WebClient webClient(@Value(value = "${joke.source}") String jokeSource) {
        return WebClient.builder().baseUrl(jokeSource).build();
    }

    @ExceptionHandler(ExceptionCount.class)
    public ResponseEntity<String> handleCustomBadRequest(ExceptionCount e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
