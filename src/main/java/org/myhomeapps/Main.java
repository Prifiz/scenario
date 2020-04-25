package org.myhomeapps;

import org.myhomeapps.adapters.CalcAdapter;
import org.myhomeapps.walkers.GraphBasedMenuWalker;
import org.myhomeapps.walkers.MenuWalker;

import java.io.IOException;

public class Main  {

    public static void main(String[] args) throws IOException {
        MenuWalker walker = new GraphBasedMenuWalker();
        walker.registerAdapter(new CalcAdapter());
        walker.run();
    }

}
