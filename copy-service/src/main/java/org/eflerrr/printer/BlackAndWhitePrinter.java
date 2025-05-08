package org.eflerrr.printer;

import org.eflerrr.state.PrinterState;

import static org.eflerrr.utils.PrinterUtils.getRandomState;

public class BlackAndWhitePrinter extends Printer {

    private static final InkType inkType = InkType.BLACK_AND_WHITE;

    public BlackAndWhitePrinter(String id, PrinterState state) {
        super(id, inkType, state);
    }

    public BlackAndWhitePrinter(String id) {
        super(id, inkType, getRandomState());
    }

}
