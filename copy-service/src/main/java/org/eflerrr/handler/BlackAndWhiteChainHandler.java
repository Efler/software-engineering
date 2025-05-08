package org.eflerrr.handler;

import org.eflerrr.model.PrintRequest;
import org.eflerrr.model.file.Document;
import org.eflerrr.model.file.Photo;
import org.eflerrr.printer.BlackAndWhitePrinter;
import org.eflerrr.state.DocPrinterState;
import org.eflerrr.state.PicPrinterState;
import org.eflerrr.utils.LogUtils;
import org.slf4j.event.Level;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.eflerrr.utils.LogUtils.logColored;

public class BlackAndWhiteChainHandler extends ChainHandler {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final BlackAndWhitePrinter printer;

    public BlackAndWhiteChainHandler(BlackAndWhitePrinter printer) {
        this.printer = printer;
    }

    @Override
    public void handle(PrintRequest req) {
        if (!req.color()) {
            logColored("[BW-CHAIN-HANDLER] B&W handles request[%s]"
                            .formatted(req.id()),
                    LogUtils.LogColor.BLUE, Level.DEBUG);
            executor.execute(() -> {
                printer.setState(
                        switch (req.type()) {
                            case DOCUMENT -> new DocPrinterState(((Document) req.file()).size());
                            case PHOTO -> new PicPrinterState(((Photo) req.file()).format());
                        }
                );
                printer.print(req);
            });
        } else if (next != null) {
            logColored("[BW-CHAIN-HANDLER] B&W skips request[%s] due to color –> next"
                            .formatted(req.id()),
                    LogUtils.LogColor.BLUE, Level.DEBUG);
            next.handle(req);
        }
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
