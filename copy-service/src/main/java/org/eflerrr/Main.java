package org.eflerrr;

import lombok.extern.slf4j.Slf4j;
import org.eflerrr.handler.BlackAndWhiteChainHandler;
import org.eflerrr.handler.ColoredChainHandler;
import org.eflerrr.model.PrintRequest;
import org.eflerrr.model.file.Document;
import org.eflerrr.model.file.Photo;
import org.eflerrr.printer.BlackAndWhitePrinter;
import org.eflerrr.printer.ColoredPrinter;
import org.eflerrr.printer.proxy.ColoredPrinterProxy;
import org.eflerrr.service.FixedPhotoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

@Slf4j
public class Main {
    private static final Random RANDOM = new Random();
    public static void main(String[] args) throws InterruptedException {


        // ------- printers ------- //

        var bwPrinter = new BlackAndWhitePrinter("bw-p#1");
        var coloredPrinter = new ColoredPrinter("colored-p#1");
        var proxy = new ColoredPrinterProxy(coloredPrinter, new FixedPhotoService(Photo.Format.F_21x30));

        var handlers = List.of(
                new BlackAndWhiteChainHandler(bwPrinter),
                new ColoredChainHandler(proxy)
        );
        for (var i = 0; i < handlers.size() - 1; i++) {
            handlers.get(i).setNext(handlers.get(i + 1));
        }
        var entrypoint = handlers.getFirst();


        // ------- files ------- //

        var doc = new Document(
                "doc.pdf",
                LocalDate.of(2012, 12, 12).atStartOfDay(),
                Document.Size.A4
        );
        var photo = new Photo(
                "photo.jpg",
                LocalDate.of(2024, 1, 1).atStartOfDay(),
                Photo.Format.F_10x15
        );


        // ------- requests ------- //

        var requests = new ArrayList<>(List.of(
                new PrintRequest(1, PrintRequest.Type.DOCUMENT, doc, false),
                new PrintRequest(2, PrintRequest.Type.PHOTO, photo, true),
                new PrintRequest(3, PrintRequest.Type.PHOTO, null, true)
        ));
        Collections.shuffle(requests);


        // ------- processing ------- //

        try (var firings = Executors.newFixedThreadPool(3)) {
            for (var req : requests) {
                firings.submit(() -> {
                    log.info("[MAIN] Firing request: {}", req);
                    entrypoint.handle(req);
                });
                Thread.sleep(RANDOM.nextInt(1000));
            }
        }

        for (var handler : handlers) {
            handler.shutdownWork();
        }
        for (var handler : handlers) {
            handler.awaitWork();
        }

        log.info("[MAIN] All requests processed");
    }
}
