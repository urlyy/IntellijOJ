package com.lyy.intellijoj.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EchoHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(final WebSocketSession session) {
        Flux<WebSocketMessage> output = session.receive()
                .map(value -> session.textMessage("发送成功：" + value.getPayloadAsText()));
        return session.send(output);
    }
}

