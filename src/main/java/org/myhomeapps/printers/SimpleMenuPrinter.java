package org.myhomeapps.printers;

import org.myhomeapps.MenuFrame;

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
