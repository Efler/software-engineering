package org.eflerrr.utils;

import org.eflerrr.model.file.Document;
import org.eflerrr.model.file.Photo;
import org.eflerrr.state.DocPrinterState;
import org.eflerrr.state.PicPrinterState;
import org.eflerrr.state.PrinterState;
import org.eflerrr.utils.LogUtils.LogColor;

import java.util.Random;
import java.util.UUID;

import static org.eflerrr.utils.LogUtils.logColored;

public final class PrinterUtils {
    private final static Random RANDOM = new Random();

    private PrinterUtils() {
    }

    public static void work(long seconds, String message, LogColor logColor) {
        for (int i = 0; i < seconds; i++) {
            try {
                if (message != null) {
                    logColored(message, logColor);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public static void work(long seconds) {
        work(seconds, null, null);
    }

    public static PrinterState getRandomState() {
        if (RANDOM.nextBoolean()) {
            return new DocPrinterState(
                    Document.Size.values()[RANDOM.nextInt(Document.Size.values().length)]
            );
        } else {
            return new PicPrinterState(
                    Photo.Format.values()[RANDOM.nextInt(Photo.Format.values().length)]
            );
        }
    }

    public static String getRandomId() {
        return UUID.randomUUID().toString();
    }
}
