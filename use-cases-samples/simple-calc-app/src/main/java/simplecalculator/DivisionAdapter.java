package simplecalculator;

import org.myhomeapps.adapters.CommandLineAdapter;

public class DivisionAdapter extends CommandLineAdapter {

    private String divFirst;
    private String divSecond;

    Calculator calculator = new CalculatorImpl();

    public String divide() {
        int first = Integer.parseInt(divFirst);
        int second = Integer.parseInt(divSecond);
        String result = String.valueOf(calculator.divide(first, second));
        System.out.println(result);
        return result;
    }
}
