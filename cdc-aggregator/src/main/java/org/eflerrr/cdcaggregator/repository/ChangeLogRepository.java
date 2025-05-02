package org.eflerrr.cdcaggregator.repository;

import lombok.RequiredArgsConstructor;
import org.eflerrr.cdcaggregator.model.event.DataDeleteEvent;
import org.eflerrr.cdcaggregator.model.event.DataInsertEvent;
import org.eflerrr.cdcaggregator.model.event.DataUpdateEvent;
import org.eflerrr.cdcaggregator.model.event.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChangeLogRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Event> fetchNewEvents(OffsetDateTime lastCheck) {
        String sql = """
                SELECT table_name, record_id, new_data, old_data, operation, changed_at
                FROM change_log
                WHERE changed_at > ?
                ORDER BY changed_at;
                """;
        return jdbcTemplate.query(sql, ps -> ps.setObject(1, lastCheck), (rs, rn) -> {
            var tableName = rs.getString("table_name");
            var recordId = rs.getInt("record_id");
            var newData = rs.getString("new_data");
            var oldData = rs.getString("old_data");
            var changedAt = rs.getObject("changed_at", OffsetDateTime.class);
            String operation = rs.getString("operation");

            return switch (operation) {
                case "INSERT" -> new DataInsertEvent(tableName, recordId, newData, changedAt);
                case "UPDATE" -> new DataUpdateEvent(tableName, recordId, oldData, newData, changedAt);
                case "DELETE" -> new DataDeleteEvent(tableName, recordId, oldData, changedAt);
                default -> throw new IllegalArgumentException("Unknown operation: " + operation);
            };
        });
    }

}
