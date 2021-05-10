package com.example.blogapp.service;

import com.example.blogapp.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SomeOtherApi {

    private final RestTemplate restTemplate;

    @Async
    public CompletableFuture<String> getImportantData() {
        Message message = restTemplate.getForObject("https://mocki.io/v1/f2d9224c-439d-494f-b2ee-6f05876a3297", Message.class);

        log.info(" --------------");
        log.debug("DEBUG MESSAGE: " + message.getMessage());
        log.info(" -------------");
        return CompletableFuture.completedFuture(message.getMessage());
    }


}
