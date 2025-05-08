package org.eflerrr.service;

import lombok.RequiredArgsConstructor;
import org.eflerrr.model.file.Photo;
import org.eflerrr.utils.LogUtils.LogColor;

import java.time.LocalDateTime;

import static org.eflerrr.utils.LogUtils.logColored;
import static org.eflerrr.utils.PrinterUtils.work;

@RequiredArgsConstructor
public class FixedPhotoService implements PhotoService {

    private final Photo.Format format;

    @Override
    public Photo makePhoto() {
        var date = LocalDateTime.now();
        logColored("[FIXED-PHOTO-SERVICE] Creating photo[date=%s, format=%s] ..."
                        .formatted(date, format),
                LogColor.YELLOW);
        work(3, "[FIXED-PHOTO-SERVICE] *camera flashing* ...", LogColor.YELLOW);
        logColored("[FIXED-PHOTO-SERVICE] Creating photo[date=%s, format=%s] --- DONE!"
                        .formatted(date, format),
                LogColor.YELLOW);
        return new Photo(
                "photo-" + date, date, format);
    }
}
