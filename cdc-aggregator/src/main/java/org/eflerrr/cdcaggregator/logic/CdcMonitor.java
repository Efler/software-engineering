package org.eflerrr.cdcaggregator.logic;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eflerrr.cdcaggregator.logic.aggregator.EventAggregator;
import org.eflerrr.cdcaggregator.model.event.Event;
import org.eflerrr.cdcaggregator.repository.ChangeLogRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CdcMonitor {
    private final EventAggregator aggregator;
    private final ChangeLogRepository changeLogRepository;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private OffsetDateTime lastCheck = OffsetDateTime.now();

    @PostConstruct
    public void start() {
        scheduler.scheduleAtFixedRate(
                this::processEvents, 0, 1, TimeUnit.SECONDS);
        log.info("CDC Monitor started");
    }

    @PreDestroy
    public void stop() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
        log.info("CDC Monitor stopped");
    }

    private void processEvents() {
        List<Event> events = changeLogRepository.fetchNewEvents(lastCheck);
        if (!events.isEmpty()) {
            lastCheck = OffsetDateTime.now();
            events.forEach(aggregator::publish);
        }
    }

}
