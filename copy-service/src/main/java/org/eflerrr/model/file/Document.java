package org.eflerrr.model.file;

import java.time.LocalDateTime;

public record Document(
        String name,
        LocalDateTime createdAt,
        Size size
) implements File {
    public enum Size {
        A5,
        A4,
        A3,
        A2
    }
}
