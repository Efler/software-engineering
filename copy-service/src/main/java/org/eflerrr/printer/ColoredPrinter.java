package org.eflerrr.printer;

import org.eflerrr.state.PrinterState;

import static org.eflerrr.utils.PrinterUtils.getRandomId;
import static org.eflerrr.utils.PrinterUtils.getRandomState;

public class ColoredPrinter extends Printer {

    private static final InkType inkType = InkType.COLORED;

    public ColoredPrinter(String id, PrinterState state) {
        super(id, inkType, state);
    }

    public ColoredPrinter(String id) {
        super(id, inkType, getRandomState());
    }

    public ColoredPrinter() {
        super(getRandomId(), inkType, getRandomState());
    }

}
