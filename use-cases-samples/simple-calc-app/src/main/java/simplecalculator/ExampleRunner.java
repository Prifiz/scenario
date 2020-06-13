package simplecalculator;

import org.myhomeapps.walkers.MenuWalkerInitiator;

import java.io.IOException;
import java.io.InputStream;

public class ExampleRunner {

    public void run() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("calculatorMenu.yml");

        MenuWalkerInitiator.initMenu(inputStream)
                .registerAdapter(new AdditionAdapter())
                .registerAdapter(new DivisionAdapter())
                // TODO register custom input rule
                .run();
    }
}
