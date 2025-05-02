package org.eflerrr.cdcaggregator.logic.aggregator;

import org.eflerrr.cdcaggregator.logic.listener.EventListener;
import org.eflerrr.cdcaggregator.model.event.Event;

public interface EventAggregator {
    <E extends Event> void registerListener(Class<E> eventType, EventListener<E> listener);
    void publish(Event event);
}
