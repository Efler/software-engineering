package org.eflerrr.state;

import org.eflerrr.model.file.Photo;
import org.eflerrr.utils.LogUtils;

import static org.eflerrr.utils.LogUtils.logColored;
import static org.eflerrr.utils.PrinterUtils.work;

public record PicPrinterState(
        Photo.Format format
) implements PrinterState {
    private final static String STATE_NAME = "PicPrinterState";

    @Override
    public String toString() {
        return STATE_NAME + ":" + format;
    }

    @Override
    public void apply(String printerId) {
        logColored("[PRINTER: %s] Applying PicPrinterState[format=%s] ..."
                        .formatted(printerId, format),
                LogUtils.LogColor.CYAN);
        work(1);
        logColored("[PRINTER: %s] PicPrinterState[format=%s] applied :)"
                        .formatted(printerId, format),
                LogUtils.LogColor.CYAN);
    }

}
