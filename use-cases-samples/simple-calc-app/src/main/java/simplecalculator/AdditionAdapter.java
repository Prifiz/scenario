package simplecalculator;

import org.prifizapps.adapters.CommandLineAdapter;

public class AdditionAdapter implements CommandLineAdapter {

    private String addFirst;
    private String addSecond;

    Calculator calculator = new CalculatorImpl();

    public String execute() {
        int first = Integer.parseInt(addFirst);
        int second = Integer.parseInt(addSecond);
        String result = String.valueOf(calculator.add(first, second));
//        System.out.println(result);
        return result;
    }
}
