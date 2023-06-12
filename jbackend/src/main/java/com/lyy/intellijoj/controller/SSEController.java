package com.lyy.intellijoj.controller;

import com.lyy.intellijoj.common.Response;
import com.lyy.intellijoj.sse.SSEManager;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
public class SSEController {
    private final SSEManager sseManager = new SSEManager();

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> sse() {
        System.out.println("建立了链接");
        return sseManager.getEventStream();
    }

    @GetMapping("/push")
    public void pushMessage() {
        String message = "qwerqwer";
        System.out.println(message);
        sseManager.pushMessage(message);
    }
}