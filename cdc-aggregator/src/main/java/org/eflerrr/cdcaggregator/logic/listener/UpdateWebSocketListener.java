package org.eflerrr.cdcaggregator.logic.listener;

import lombok.RequiredArgsConstructor;
import org.eflerrr.cdcaggregator.config.websocket.WebSocketHandler;
import org.eflerrr.cdcaggregator.model.event.DataUpdateEvent;

@RequiredArgsConstructor
public class UpdateWebSocketListener implements EventListener<DataUpdateEvent> {
    private final WebSocketHandler wsHandler;

    @Override
    public void onEvent(DataUpdateEvent event) {
        wsHandler.broadcast(
                "[UPDATE][%s][%s] : %s -> %s".formatted(
                        event.getTableName(),
                        event.getUpdatedAt(),
                        event.getOldDataJson(),
                        event.getNewDataJson()
                )
        );
    }
}
