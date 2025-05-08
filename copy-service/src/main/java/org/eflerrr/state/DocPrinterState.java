package org.eflerrr.state;

import org.eflerrr.model.file.Document;
import org.eflerrr.utils.LogUtils;

import static org.eflerrr.utils.LogUtils.logColored;
import static org.eflerrr.utils.PrinterUtils.work;

public record DocPrinterState(
        Document.Size size
) implements PrinterState {
    private final static String STATE_NAME = "DocPrinterState";

    @Override
    public String toString() {
        return STATE_NAME + ":" + size;
    }

    @Override
    public void apply(String printerId) {
        logColored("[PRINTER: %s] Applying DocPrinterState[size=%s] ..."
                        .formatted(printerId, size),
                LogUtils.LogColor.CYAN);
        work(1);
        logColored("[PRINTER: %s] DocPrinterState[size=%s] applied :)"
                        .formatted(printerId, size),
                LogUtils.LogColor.CYAN);
    }

}
