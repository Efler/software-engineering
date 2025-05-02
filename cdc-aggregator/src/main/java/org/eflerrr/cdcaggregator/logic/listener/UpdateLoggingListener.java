package org.eflerrr.cdcaggregator.logic.listener;

import lombok.extern.slf4j.Slf4j;
import org.eflerrr.cdcaggregator.model.event.DataUpdateEvent;

@Slf4j
public class UpdateLoggingListener implements EventListener<DataUpdateEvent> {
    @Override
    public void onEvent(DataUpdateEvent event) {
        log.info("[UPDATE][{}][{}] : {} -> {}",
                event.getTableName(), event.getUpdatedAt(), event.getOldDataJson(), event.getNewDataJson());
    }
}
