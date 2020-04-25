package org.myhomeapps.printers;

import org.myhomeapps.formatters.SimpleMenuFormatter;
import org.myhomeapps.menuentities.MenuFrame;

import java.io.PrintStream;

public class FormattedMenuPrinter implements MenuPrinter {

    private final SimpleMenuFormatter formatter;
    private final PrintStream out;

    public FormattedMenuPrinter(SimpleMenuFormatter formatter, PrintStream out) {
        this.formatter = formatter;
        this.out = out;
    }

    @Override
    public void print(MenuFrame frame) {
        out.print(formatter.format(frame));
    }
}
