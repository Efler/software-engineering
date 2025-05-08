package org.eflerrr.printer.proxy;

import org.eflerrr.model.PrintRequest;
import org.eflerrr.printer.ColoredPrinter;
import org.eflerrr.service.PhotoService;
import org.eflerrr.state.PicPrinterState;
import org.eflerrr.state.PrinterState;
import org.eflerrr.utils.LogUtils.LogColor;

import static org.eflerrr.utils.LogUtils.logColored;

public class ColoredPrinterProxy extends ColoredPrinter {

    private final ColoredPrinter realPrinter;
    private final PhotoService photoService;

    public ColoredPrinterProxy(ColoredPrinter printer, PhotoService photoService) {
        super("proxy");
        this.realPrinter = printer;
        this.photoService = photoService;
    }

    @Override
    public void setState(PrinterState state) {
        realPrinter.setState(state);
    }

    @Override
    public void print(PrintRequest req) {
        logColored(
                "[PRINTER-PROXY] Proxying request[%s] for printer[%s]"
                        .formatted(req.id(), realPrinter.getId()),
                LogColor.PURPLE);
        if (req.type() == PrintRequest.Type.PHOTO && req.file() == null) {
            logColored(
                    "[PRINTER-PROXY] Making photo with PhotoService for request[%s] to printer[%s] "
                            .formatted(req.id(), realPrinter.getId()),
                    LogColor.PURPLE);
            var photo = photoService.makePhoto();
            realPrinter.setState(new PicPrinterState(photo.format()));
            realPrinter.print(
                    new PrintRequest(
                            req.id(),
                            req.type(),
                            photo,
                            req.color()
                    )
            );
        } else {
            realPrinter.print(req);
        }
    }
}
