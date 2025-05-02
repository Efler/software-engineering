package org.eflerrr.cdcaggregator.logic.listener;

import lombok.extern.slf4j.Slf4j;
import org.eflerrr.cdcaggregator.model.event.DataInsertEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
public class InsertFileListener implements EventListener<DataInsertEvent> {
    private final Path filePath;

    public InsertFileListener(Path filePath) throws IOException {
        this.filePath = filePath;
        if (Files.notExists(this.filePath)) {
            Files.createFile(this.filePath);
        }
    }

    @Override
    public void onEvent(DataInsertEvent event) {
        try {
            Files.writeString(
                    filePath,
                    "[INSERT][%s][%s] : %s%n".formatted(
                            event.getTableName(),
                            event.getInsertedAt(),
                            event.getNewDataJson()
                    ),
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            log.error("Failed to write to file: {}", filePath, e);
        }
    }
}
