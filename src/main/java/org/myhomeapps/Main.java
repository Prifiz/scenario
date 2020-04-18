package org.myhomeapps;

import org.myhomeapps.adapters.CalcAdapter;

import java.io.IOException;

public class Main  {

    public static void main(String[] args) {

        CommandLine commandLine;
        try {
            commandLine = new SimpleCommandLineImpl();
            CalcAdapter calcAdapter = new CalcAdapter();
            commandLine.getAllFrames().forEach(frame -> frame.addObserver(calcAdapter));
            commandLine.run();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
