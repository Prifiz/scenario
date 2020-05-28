package simplecalculator;

import org.myhomeapps.walkers.GraphBasedMenuWalker;
import org.myhomeapps.walkers.InputAsker;

import java.io.IOException;
import java.io.InputStream;

public class ExampleRunner {

    public void run() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("calculatorMenu.yml");
        InputAsker inputAsker = new InputAsker(System.in, System.out);
        new GraphBasedMenuWalker(inputStream, inputAsker)
                .registerAdapter(new AdditionAdapter())
                .registerAdapter(new DivisionAdapter())
                .run();
    }
}
