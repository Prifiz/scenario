package org.prifizapps.printers;

import org.prifizapps.menuentities.MenuFrame;

import java.io.PrintStream;

public class SimpleMenuPrinter implements MenuPrinter {

    private PrintStream out;

    public SimpleMenuPrinter(PrintStream out) {
        this.out = out;
    }

    public void print(MenuFrame frame) {
        out.print(frame.toString());
    }

}
