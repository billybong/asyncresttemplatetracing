package com.tracing.bug;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class Endpoint {

    private final AsyncRestTemplate asyncRestTemplate;

    public Endpoint(AsyncRestTemplate asyncRestTemplate) {
        this.asyncRestTemplate = asyncRestTemplate;
    }

    @GetMapping("test")
    public MyResponse doit(){
        IntStream.range(0,10).forEach(__ -> {
            try {
                ResponseEntity<String> answer = asyncRestTemplate.getForEntity("http://www.google.com", String.class, Collections.emptyMap()).get();
                log.info("Response from Google...");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        return new MyResponse();
    }

    public class MyResponse{
        public String status = "ok";
    }
}
