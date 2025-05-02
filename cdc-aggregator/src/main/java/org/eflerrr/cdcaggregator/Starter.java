package org.eflerrr.cdcaggregator;

import org.eflerrr.cdcaggregator.config.websocket.WebSocketHandler;
import org.eflerrr.cdcaggregator.logic.aggregator.CdcEventAggregator;
import org.eflerrr.cdcaggregator.logic.listener.*;
import org.eflerrr.cdcaggregator.model.event.DataDeleteEvent;
import org.eflerrr.cdcaggregator.model.event.DataInsertEvent;
import org.eflerrr.cdcaggregator.model.event.DataUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class Starter {

    private final CdcEventAggregator aggregator;
    private final WebSocketHandler wsHandler;
    private final Path filepath;

    @Autowired
    public Starter(
            CdcEventAggregator aggregator,
            WebSocketHandler wsHandler,
            @Value("${app.file-listener.filepath}")
            String filepath
    ) {
        this.aggregator = aggregator;
        this.wsHandler = wsHandler;
        this.filepath = Path.of(filepath);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() throws IOException {

        aggregator.registerListener(
                DataInsertEvent.class, new InsertLoggingListener());
        aggregator.registerListener(
                DataInsertEvent.class, new InsertFileListener(filepath));


        aggregator.registerListener(
                DataUpdateEvent.class, new UpdateLoggingListener());
        aggregator.registerListener(
                DataUpdateEvent.class, new UpdateWebSocketListener(wsHandler));


        aggregator.registerListener(
                DataDeleteEvent.class, new DeleteLoggingListener()
        );

    }
}
