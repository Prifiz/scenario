package org.myhomeapps.printers;

import org.myhomeapps.SimpleTextFormatter;
import org.myhomeapps.menumodel.MenuFrame;

import java.io.PrintStream;

public class FormattedMenuPrinter implements MenuPrinter {

    private final SimpleTextFormatter formatter;
    private final PrintStream out;

    public FormattedMenuPrinter(SimpleTextFormatter formatter, PrintStream out) {
        this.formatter = formatter;
        this.out = out;
    }

    @Override
    public void print(MenuFrame frame) {
        out.print(formatter.format(frame));
    }
}
