package org.eflerrr.cdcaggregator.controller;

import lombok.RequiredArgsConstructor;
import org.eflerrr.cdcaggregator.config.websocket.WebSocketHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Deprecated
public class WebhookController {

    private final WebSocketHandler wsHandler;

    @GetMapping("/message")
    public void postMessage(@RequestParam String text) {
        wsHandler.broadcast(text);
    }
}
