package org.eflerrr.cdcaggregator.model.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class DataInsertEvent extends Event {
    private final String newDataJson;
    private final OffsetDateTime insertedAt;

    public DataInsertEvent(
            String tableName,
            int recordId,
            String newDataJson,
            OffsetDateTime insertedAt
    ) {
        super(tableName, recordId);
        this.newDataJson = newDataJson;
        this.insertedAt = insertedAt;
    }
}
