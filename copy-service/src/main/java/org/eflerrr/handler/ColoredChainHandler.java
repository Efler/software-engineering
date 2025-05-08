package org.eflerrr.handler;

import org.eflerrr.model.PrintRequest;
import org.eflerrr.model.file.Document;
import org.eflerrr.model.file.Photo;
import org.eflerrr.printer.ColoredPrinter;
import org.eflerrr.state.DocPrinterState;
import org.eflerrr.state.PicPrinterState;
import org.eflerrr.utils.LogUtils.LogColor;
import org.slf4j.event.Level;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.eflerrr.utils.LogUtils.logColored;

public class ColoredChainHandler extends ChainHandler {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ColoredPrinter printer;

    public ColoredChainHandler(ColoredPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void handle(PrintRequest req) {
        logColored("[COLORED-CHAIN-HANDLER] COLORED handles request[%s]"
                        .formatted(req.id()),
                LogColor.BLUE, Level.DEBUG);
        executor.execute(() -> {
            if (req.file() != null) {
                printer.setState(
                        switch (req.type()) {
                            case DOCUMENT -> new DocPrinterState(((Document) req.file()).size());
                            case PHOTO -> new PicPrinterState(((Photo) req.file()).format());
                        }
                );
            }
            printer.print(req);
        });
    }

    @Override
    public void shutdownWork() {
        executor.shutdown();
    }

    @Override
    public void awaitWork() throws InterruptedException {
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

}
