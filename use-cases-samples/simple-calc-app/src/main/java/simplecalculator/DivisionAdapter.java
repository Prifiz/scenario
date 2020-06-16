package simplecalculator;

import org.prifizapps.adapters.CommandLineAdapter;

public class DivisionAdapter implements CommandLineAdapter {

    private String divFirst;
    private String divSecond;

    Calculator calculator = new CalculatorImpl();

    public String execute() {
        int first = Integer.parseInt(divFirst);
        int second = Integer.parseInt(divSecond);
        String result = String.valueOf(calculator.divide(first, second));
//        System.out.println(result);
        return result;
    }
}
