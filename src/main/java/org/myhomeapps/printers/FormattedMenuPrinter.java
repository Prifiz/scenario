package org.myhomeapps.printers;

import org.myhomeapps.MenuFrame;
import org.myhomeapps.SimpleTextFormatter;

import java.io.PrintStream;

public class FormattedMenuPrinter implements MenuPrinter {

    private SimpleTextFormatter formatter;
    private PrintStream out;

    public FormattedMenuPrinter(SimpleTextFormatter formatter, PrintStream out) {
        this.formatter = formatter;
        this.out = out;
    }

    @Override
    public void print(MenuFrame frame) {
        out.print(formatter.format(frame));
    }
}
