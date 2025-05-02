package org.eflerrr.cdcaggregator.model.event;

import lombok.*;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class Event {
    private final String tableName;
    private final int recordId;
}
