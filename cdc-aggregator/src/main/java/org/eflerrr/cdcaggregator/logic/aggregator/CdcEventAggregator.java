package org.eflerrr.cdcaggregator.logic.aggregator;

import org.eflerrr.cdcaggregator.logic.listener.EventListener;
import org.eflerrr.cdcaggregator.model.event.Event;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CdcEventAggregator implements EventAggregator {
    private final Map<Class<? extends Event>, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();

    @Override
    public <E extends Event> void registerListener(Class<E> eventType, EventListener<E> listener) {
        listeners.computeIfAbsent(
                        eventType, k -> new CopyOnWriteArrayList<>())
                .add(listener);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void publish(Event event) {
        List<EventListener<?>> list = listeners.getOrDefault(event.getClass(), List.of());
        for (EventListener<?> rawListener : list) {
            ((EventListener<Event>) rawListener).onEvent(event);
        }
    }
}
