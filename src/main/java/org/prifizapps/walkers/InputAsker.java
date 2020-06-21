package org.prifizapps.walkers;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputAsker {

    private final Scanner scanner;
    private final PrintStream out;

    public InputAsker(InputStream in, PrintStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }

    public void printAskMessage(String message) {
        out.print(message);
    }

    public String ask(String message) {
        printAskMessage(message);
        return scanner.nextLine();
    }
}
