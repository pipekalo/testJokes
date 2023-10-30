package com.example.demo.controller;

import com.example.demo.exceptions.ExceptionCount;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    private final WebClient webClient;

    public Controller(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/jokes/{count}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<String>> getJoke(@PathVariable int count) {
        if (count > 100) {
            throw new ExceptionCount();
        }
        List<Flux<String>> result = new ArrayList<>();
        int times = 0;
        int last = count;
        if (count > 10) {
            times = count / 10;
            last = count % 10;
        }
        for (int i = 0; i < times; i++) {
            Flux<String> response = Flux.range(1, 10)
                    .flatMap(integer -> webClient.get().uri("/random_joke").retrieve().bodyToMono(String.class))
                    .map(s -> s + "\n")
                    .log();
            result.add(response);
        }
        Flux<String> response = Flux.range(1, last)
                .flatMap(integer -> webClient.get().uri("/random_joke").retrieve().bodyToMono(String.class))
                .map(s -> s + "\n")
                .log();
        result.add(response);
        return ResponseEntity.ok(Flux.fromIterable(result).flatMap(stringFlux -> stringFlux));
    }
}
