package org.eflerrr.cdcaggregator.model.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class DataUpdateEvent extends Event {
    private final String oldDataJson;
    private final String newDataJson;
    private final OffsetDateTime updatedAt;

    public DataUpdateEvent(
            String tableName,
            int recordId,
            String oldDataJson,
            String newDataJson,
            OffsetDateTime updatedAt
    ) {
        super(tableName, recordId);
        this.oldDataJson = oldDataJson;
        this.newDataJson = newDataJson;
        this.updatedAt = updatedAt;
    }
}
