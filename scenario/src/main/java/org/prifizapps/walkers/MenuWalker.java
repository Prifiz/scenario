package org.prifizapps.walkers;

import org.prifizapps.adapters.CommandLineAdapter;
import org.prifizapps.menuentities.input.AbstractInputRule;

import java.io.IOException;


public interface MenuWalker {


    MenuWalker registerAdapter(CommandLineAdapter adapter);
    MenuWalker withCustomInputProcessors(AbstractInputRule... processors);

    /**
     * You can disable the default menu system validations by calling this method.
     * In-built validation is <b>enabled</b> by default.
     * The in-build validation is performed by {@link org.prifizapps.walkers.validators.AbstractValidator} subclasses.
     * @return {@link MenuWalker} object for proceeding the configuration or run CLI menu.
     */
    MenuWalker disableInBuiltValidation();

    /**
     * Enables the default menu system validations if it was previously disabled.
     * In-built validation is <b>enabled</b> by default.
     * The in-build validation is performed by {@link org.prifizapps.walkers.validators.AbstractValidator} subclasses.
     * @return {@link MenuWalker} object for proceeding the configuration or run CLI menu.
     */
    MenuWalker enableInBuiltValidation();

    /**
     * Runs the CLI menu.
     * @throws IOException if something goes wrong during the CLI menu execution
     * (e.g. validation failed or there're some errors during the menu initialization)
     */
    void run() throws IOException;
}
