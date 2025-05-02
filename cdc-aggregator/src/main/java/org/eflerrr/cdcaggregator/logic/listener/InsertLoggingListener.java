package org.eflerrr.cdcaggregator.logic.listener;

import lombok.extern.slf4j.Slf4j;
import org.eflerrr.cdcaggregator.model.event.DataInsertEvent;

@Slf4j
public class InsertLoggingListener implements EventListener<DataInsertEvent> {
    @Override
    public void onEvent(DataInsertEvent event) {
        log.info("[INSERT][{}][{}] : {}",
                event.getTableName(), event.getInsertedAt(), event.getNewDataJson());
    }
}
