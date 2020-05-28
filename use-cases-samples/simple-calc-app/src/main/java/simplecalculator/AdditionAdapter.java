package simplecalculator;

import org.myhomeapps.adapters.CommandLineAdapter;

public class AdditionAdapter extends CommandLineAdapter {

    private String addFirst;
    private String addSecond;

    Calculator calculator = new CalculatorImpl();

    public String add() {
        int first = Integer.parseInt(addFirst);
        int second = Integer.parseInt(addSecond);
        String result = String.valueOf(calculator.add(first, second));
        System.out.println(result);
        return result;
    }
}
