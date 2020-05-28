package simplecalculator;

public class CalculatorImpl implements Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public float divide(int a, int b) {
        return (float)a / b;
    }
}
