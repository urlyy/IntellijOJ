package com.lyy.intellijoj.sse;


import org.springframework.context.event.EventListener;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.server.ServletServerHttpRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class SSEManager {
    private final Sinks.Many<ServerSentEvent<String>> sink;
    private final Flux<ServerSentEvent<String>> eventStream;

    public SSEManager() {
        this.sink = Sinks.many().multicast().directBestEffort();
        this.eventStream = sink.asFlux().share();
    }

    public void pushMessage(String message) {
        ServerSentEvent<String> event = ServerSentEvent.builder(message).build();
        sink.tryEmitNext(event);
    }

    public Flux<ServerSentEvent<String>> getEventStream() {
        return eventStream;
    }
}
