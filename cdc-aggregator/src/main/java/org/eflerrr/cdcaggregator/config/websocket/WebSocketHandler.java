package org.eflerrr.cdcaggregator.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(
            @NonNull WebSocketSession session,
            @NonNull CloseStatus status) {
        sessions.remove(session);
    }

    public void broadcast(String msg) {
        sessions.forEach(sess -> {
            if (sess.isOpen()) {
                try {
                    sess.sendMessage(new TextMessage(msg));
                } catch (Exception e) {
                    log.error("Error sending message in websocket handler to session: {}", sess.getId(), e);
                }
            }
        });
    }
}
