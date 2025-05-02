package org.eflerrr.cdcaggregator.model.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class DataDeleteEvent extends Event {
    private final String oldDataJson;
    private final OffsetDateTime deletedAt;

    public DataDeleteEvent(
            String tableName,
            int recordId,
            String oldDataJson,
            OffsetDateTime deletedAt
    ) {
        super(tableName, recordId);
        this.oldDataJson = oldDataJson;
        this.deletedAt = deletedAt;
    }
}
