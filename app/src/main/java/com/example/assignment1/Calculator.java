package com.example.assignment1;

import android.view.View;
import android.widget.Button;


public class Calculator {
    private double num1, num2;

    public double add(String addends1, String addends2) {
        double sum = 0.0;

        try {
            num1 = Double.parseDouble(addends1);
            num2 = Double.parseDouble(addends2);

            sum = num1 + num2;
        }

        catch (Exception ignored) {}

        return sum;
    }

    public double subtract(String minuend, String subtrahend) {
        double difference = 0.0;

        try {
            num1 = Double.parseDouble(minuend);
            num2 = Double.parseDouble(subtrahend);

            difference = num1 - num2;
        }
        catch (Exception ignored) {}

        return difference;
    }

    public double multiply(String multiplicand, String multiplier) {
        double product = 0.0;

        try {
            num1 = Double.parseDouble(multiplicand);
            num2 = Double.parseDouble(multiplier);

            product = num1 * num2;
        }
        catch (Exception ignored) {}

        return product;
    }

    public double divide(String dividend, String divisor) {
        double quotient = 0.0;

        try {
            num1 = Double.parseDouble(dividend);
            num2 = Double.parseDouble(divisor);

            quotient = num1 / num2;
        }
        catch (Exception ignored) {}

        return quotient;
    }

    public double calculate(String number1, String number2, String operator) {
        double result = 0.0;

        switch (operator) {
            case "+":
                result = add(number1, number2);
                break;
            case "-":
                result = subtract(number1, number2);
                break;
            case "x":
                result = multiply(number1, number2);
                break;
            case "/":
                result = divide(number1, number2);
                break;
        }

        return result;

    }

}
