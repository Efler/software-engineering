package org.eflerrr.model;

import org.eflerrr.model.file.Document;
import org.eflerrr.model.file.Photo;
import org.eflerrr.model.file.File;

public record PrintRequest(
        int id,
        Type type,
        File file,
        boolean color
) {
    public PrintRequest {
        if (type == Type.DOCUMENT && !(file instanceof Document)) {
            throw new IllegalArgumentException("File should be a Document!");
        }
        if (type == Type.PHOTO && file != null && !(file instanceof Photo)) {
            throw new IllegalArgumentException("File should be a Photo!");
        }
    }

    public enum Type {
        DOCUMENT,
        PHOTO
    }
}
