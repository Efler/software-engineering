package org.eflerrr.cdcaggregator.logic.listener;

import lombok.extern.slf4j.Slf4j;
import org.eflerrr.cdcaggregator.model.event.DataDeleteEvent;

@Slf4j
public class DeleteLoggingListener implements EventListener<DataDeleteEvent> {
    @Override
    public void onEvent(DataDeleteEvent event) {
        log.info("[DELETE][{}][{}] : {}",
                event.getTableName(), event.getDeletedAt(), event.getOldDataJson());
    }
}
