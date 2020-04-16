package org.myhomeapps;

import org.myhomeapps.adapters.CalcAdapter;

public class Main  {

    public static void main(String[] args) {

        CommandLine commandLine = new SimpleCommandLineImpl();
        CalcAdapter calcAdapter = new CalcAdapter();
        commandLine.getAllFrames().forEach(frame -> frame.addObserver(calcAdapter));
        commandLine.run();
    }

}
