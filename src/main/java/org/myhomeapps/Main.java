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

        // TODO:
        /*  v 1.0 Definition Of Done:
            *
            * unit tests+
            * CI
            *
            * [x] yaml macros (@exitItem, etc.)+
            * [x] more than one home item finder
            *
            * inbuilt and custom items/frames (reuse)
            * dead-end finder+
            * endless cycle detector (cycle without exit item)
            * input control with incorrectInputMessage in yaml
            * simple text (message) item
            * no text defined in frame (menu- or item-level),
            *   i.e. user isn't shown any text and input expected
            * duplicated fields in one menu
            * re-unmarshall yaml files after each step - changes can be applied on the fly (optional, by method)
        */
    }

}
