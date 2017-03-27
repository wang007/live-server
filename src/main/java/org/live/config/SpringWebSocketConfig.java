package org.live.config;


import org.live.websocket.ChatHandshakeInterceptor;
import org.live.websocket.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by wangzhancheng on 2017/3/17.
 */
@Configuration
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(new ChatWebSocketHandler(), "/chat")
                .addInterceptors(new ChatHandshakeInterceptor()) ;

    }
}
