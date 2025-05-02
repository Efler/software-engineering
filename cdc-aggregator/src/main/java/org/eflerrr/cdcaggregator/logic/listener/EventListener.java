package org.eflerrr.cdcaggregator.logic.listener;

import org.eflerrr.cdcaggregator.model.event.Event;

public interface EventListener<E extends Event> {
    void onEvent(E event);
}
