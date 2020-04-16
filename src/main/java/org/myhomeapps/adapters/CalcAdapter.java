package org.myhomeapps.adapters;

import org.myhomeapps.testlogic.Calc;

public class CalcAdapter extends CommandLineAdapter  {

    private String addFirst;
    private String addSecond;

    Calc calc = new Calc();

    public String add() {
        int first = Integer.parseInt(addFirst);
        int second = Integer.parseInt(addSecond);
        String result = String.valueOf(calc.add(first, second));
        System.out.println(result);
        return result;
    }
}
