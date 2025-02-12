package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url = ws://ip/ws, 프로토콜이 http가 아님
        registry.addEndpoint("/ws") // 연결될 엔드포인트
                .setAllowedOrigins("*","http://localhost:3000");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지를 구독(수신)하는 엔드 포인트
        registry.enableSimpleBroker("/sub");
        // 메세지를 발행(송신)하는 엔드 포인트
        registry.setApplicationDestinationPrefixes("/pub");

        // 개인 큐 지원을 위해 `/user` 프리픽스 추가
        registry.setUserDestinationPrefix("/user");
    }
}
