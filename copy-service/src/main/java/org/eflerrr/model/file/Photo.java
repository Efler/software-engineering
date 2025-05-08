package org.eflerrr.model.file;

import java.time.LocalDateTime;

public record Photo(
        String name,
        LocalDateTime createdAt,
        Format format
) implements File {
    public enum Format {
        F_10x15,
        F_15x20,
        F_21x30,
    }
}
