package org.eflerrr.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

import static org.eflerrr.utils.LogUtils.LogColor.RESET;

@Slf4j
public final class LogUtils {

    @Getter
    public enum LogColor {
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        CYAN("\u001B[36m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        RESET("\u001B[0m");

        private final String code;

        LogColor(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public static void logColored(String message, LogColor logColor) {
        log.info("{}{}{}", logColor, message, RESET);
    }

    public static void logColored(String message, LogColor logColor, Level level) {
        String coloredMessage = String.format("%s%s%s", logColor, message, RESET);
        switch (level) {
            case TRACE -> log.trace(coloredMessage);
            case DEBUG -> log.debug(coloredMessage);
            case INFO -> log.info(coloredMessage);
            case WARN -> log.warn(coloredMessage);
            case ERROR -> log.error(coloredMessage);
        }
    }
}
