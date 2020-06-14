package org.myhomeapps.walkers;

import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.menuentities.input.AbstractInputRule;

import java.io.IOException;

public interface MenuWalker {
    MenuWalker registerAdapter(CommandLineAdapter adapter);
    MenuWalker withCustomInputProcessors(AbstractInputRule... processors);
    MenuWalker disableInBuiltValidation();
    MenuWalker enableInBuiltValidation();
    void run() throws IOException;
}
