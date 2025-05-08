package org.eflerrr.printer;

import lombok.Getter;
import org.eflerrr.model.PrintRequest;
import org.eflerrr.model.file.Document;
import org.eflerrr.model.file.Photo;
import org.eflerrr.state.DocPrinterState;
import org.eflerrr.state.PicPrinterState;
import org.eflerrr.state.PrinterState;
import org.eflerrr.utils.LogUtils.LogColor;

import static org.eflerrr.utils.LogUtils.logColored;
import static org.eflerrr.utils.PrinterUtils.work;

abstract class Printer {

    @Getter
    private final String id;
    private final InkType inkType;
    private PrinterState state;

    protected enum InkType {
        BLACK_AND_WHITE,
        COLORED
    }

    protected Printer(String id, InkType inkType, PrinterState state) {
        this.id = id;
        this.inkType = inkType;
        this.state = state;
    }

    public void setState(PrinterState state) {
        this.state = state;
        state.apply(id);
    }

    public void print(PrintRequest req) {
        checkSettings(req);
        logColored(buildLogMessage(req) + " --- STARTED...", LogColor.GREEN);
        work(5, buildLogSource(id, state, inkType) + " *working noise* ..", LogColor.GREEN);
        logColored(buildLogMessage(req) + " --- DONE!", LogColor.GREEN);
    }

    private String buildLogMessage(PrintRequest req) {
        return "%s Printing %s[requestId=%d] -> %s | %s | %s | %s".formatted(
                buildLogSource(id, state, inkType),
                req.type(), req.id(),
                req.file().name(),
                switch (req.type()) {
                    case DOCUMENT -> ((Document) req.file()).size();
                    case PHOTO -> ((Photo) req.file()).format();
                },
                "colored=" + req.color(),
                req.file().createdAt()
        );
    }

    private String buildLogSource(String id, PrinterState state, InkType inkType) {
        return "[PRINTER: %s, STATE: %s, INKS: %s]".formatted(
                id, state, inkType
        );
    }

    private void checkSettings(PrintRequest req) {
        if (req.file() == null) {
            throw new IllegalStateException("File cannot be null");
        }
        if (req.type() == PrintRequest.Type.PHOTO) {
            if (!(state instanceof PicPrinterState) || ((Photo) req.file()).format() != ((PicPrinterState) state).format()) {
                throw new IllegalStateException("Printer wrong settings!");
            }
        }
        if (req.type() == PrintRequest.Type.DOCUMENT) {
            if (!(state instanceof DocPrinterState) || ((Document) req.file()).size() != ((DocPrinterState) state).size()) {
                throw new IllegalStateException("Printer wrong settings!");
            }
        }

        logColored(
                "%s Settings check passed for request[%s] with file[%s]"
                        .formatted(buildLogSource(id, state, inkType), req.id(), req.file().name()),
                LogColor.GREEN);
    }

}
