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
            * [>] unit tests
            * [ ] CI
            *
            * [x] yaml macros (@exitItem, etc.)
            * [x] more than one home item finder
            * [x] no text defined in frame (menu- or item-level),
            *   i.e. user isn't shown any text and input expected
            * [x] acceptable input alternatives (y, yes, true, +) for frames and items
            * [x] input control with incorrectInputMessage in yaml
            * [x] dead-end finder
            * [ ] endless cycle detector (cycle without exit item)
            *
            *
            * For Future Releases:
            * [ ] menu templates
            * [ ] autocomplete input according to variants?
            * [ ] feedback menu walker - write input and/or some macro result outside to be read and processed
            * [ ] record menu walk history
            * [ ] goBack frame/item
        */
    }

}
