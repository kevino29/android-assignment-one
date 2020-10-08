package com.example.assignment1;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Calculator {
    private String input, output, num1, num2, operator, operatorBuffer;
    private ArrayList<Button> numberButtons;
    private ArrayList<Button> operatorButtons;
    private ArrayList<Button> specialButtons;
    private TextView textViewDisplay;
    private final int MAX_DIGIT = 14;
    private int digit;
    private double result, number1, number2;
    private boolean decimal, which, operatorsPressed, equalsReset, displayReset;

    public Calculator(ArrayList<Button> numberButtons, ArrayList<Button> operatorButtons,
                      ArrayList<Button> specialButtons, TextView textViewDisplay) {
        this.numberButtons = numberButtons;
        this.operatorButtons = operatorButtons;
        this.specialButtons = specialButtons;
        this.textViewDisplay = textViewDisplay;
    }

    private void init() {
        input = null;
        output = null;
        operator = null;
        operatorBuffer = null;
        num1 = "0";
        num2 = "0";
        digit = 0;
        result = 0.0;
        decimal = false;
        which = false;
        operatorsPressed = false;
        equalsReset = false;
        displayReset = false;
    }

    public void start() {
        init();

        String[] operators = new String[]{"+", "-", "x", "/"};
        for (int i = 0; i < 4; i++) {
            operatorButtons.get(i).setOnClickListener(new OperatorButtonClicked(operators[i]));
        }

        String[] specials = new String[] {"Clear", "Back", "PlusOrMinus", "Decimal", "Equals"};
        for (int i = 0; i < 5; i++) {
            specialButtons.get(i).setOnClickListener(new SpecialButtonClicked(specials[i]));
        }

        for (int i = 0; i < 10; i++) {
            numberButtons.get(i).setOnClickListener(new NumberButtonClicked(i));
        }
    }

    private class OperatorButtonClicked implements View.OnClickListener {
        private String oper;

        public OperatorButtonClicked(String oper) {
            this.oper = oper;
        }

        @Override
        public void onClick(View view) {
            if (!displayReset || equalsReset) { //prevents the spamming of operator buttons
                if (which) {
                    num2 = textViewDisplay.getText().toString();
                    operatorBuffer = oper;
                    result = calculate(num1, num2, operator);
                    num2 = String.valueOf(result);
                }

                else {
                    num1 = textViewDisplay.getText().toString();
                    operator = oper;

                    if (operatorBuffer != null) {
                        result = calculate(num2, num1, operatorBuffer);
                        num1 = String.valueOf(result);
                    }

                }
                which = !which;
                equalsReset = false;
                operatorsPressed = true;
                displayReset = true;
                displayText(String.valueOf(result));
            }
        }
    }

    private class SpecialButtonClicked implements View.OnClickListener {
        private String button;

        public SpecialButtonClicked(String button) {
            this.button = button;
        }

        @Override
        public void onClick(View view) {
            input = textViewDisplay.getText().toString();
            digit = input.length();

            switch (this.button.toUpperCase()) {
                case "CLEAR":
                    output = "0";
                    displayText(output);
                    init();
                    break;
                case "BACK":
                    if (displayReset) {
                        output = "0";
                    }

                    else {
                        if (digit > 1) {
                            StringBuilder sb = new StringBuilder(input);
                            sb.deleteCharAt(input.length()-1);
                            output = sb.toString();
                        }

                        else {
                            output = "0";
                        }
                    }

                    digit = output.length();
                    displayText(output);
                    break;
                case "PLUSORMINUS":
                    if (!displayReset) {
                        if (input.charAt(0) == '-') {
                            StringBuilder sb = new StringBuilder(input);
                            sb.deleteCharAt(0);
                            output = sb.toString();
                        }

                        else {
                            output = "-" + input;
                        }
                        digit = output.length();
                        displayText(output);
                    }

                    break;
                case "DECIMAL":
                    if (!displayReset) {
                        decimal = checkForDecimal();

                        if (!decimal) {
                            output = input + ".";
                            displayText(output);
                        }
                    }

                    else {
                        output = "0.";
                        displayText(output);
                        init();
                        equalsReset = false;
                        displayReset = false;
                    }

                    break;
                case "EQUALS": //do nothing when no operators are pressed
                    if (operatorsPressed && !displayReset) {
                        if (which) {
                            num2 = textViewDisplay.getText().toString();
                            result = calculate(num1, num2, operator);
                            num2 = String.valueOf(result);
                        } else {
                            num1 = textViewDisplay.getText().toString();

                            if (operatorBuffer != null) {
                                result = calculate(num2, num1, operatorBuffer);
                                num1 = String.valueOf(result);
                            }
                        }
                        output = String.valueOf(result);
                        displayReset = true;
                        displayText(output);
                        init();

                        try {
                            result = Double.parseDouble(textViewDisplay.getText().toString());
                        }
                        catch(Exception ignored) {
                            result = 0;
                        }

                        equalsReset = true;
                        displayReset = true;
                    }
                    break;
            }
        }
    }

    private class NumberButtonClicked implements View.OnClickListener {
        private int number;

        public NumberButtonClicked(int number) {
            this.number = number;
        }

        @Override
        public void onClick(View v) {
            input = textViewDisplay.getText().toString();
            digit = input.length();

            switch(number) {
                case 0:
                    output = "0";

                    if (digit < MAX_DIGIT) {
                        if (displayReset) {
                            output = "0";
                        }
                        else if (input.equals("-0")) {
                            if (input.charAt(0) == '-') {
                                output = "-0";
                            }
                            else {
                                output = "0";
                            }
                        }
                        else if (!input.equals("0")) {
                            output = input + "0";
                        }
                    }
                    break;
                case 1:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "1";
                        }
                        else if (input.equals("0")) {
                            output = "1";
                        }
                        else if (input.equals("-0")) {
                            output = "-1";
                        }
                        if (displayReset) {
                            output = "1";
                        }
                    }
                    break;
                case 2:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "2";
                        }
                        else if (input.equals("0")) {
                            output = "2";
                        }
                        else if (input.equals("-0")) {
                            output = "-2";
                        }
                        if (displayReset) {
                            output = "2";
                        }
                    }
                    break;
                case 3:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "3";
                        }
                        else if (input.equals("0")) {
                            output = "3";
                        }
                        else if (input.equals("-0")) {
                            output = "-3";
                        }
                        if (displayReset) {
                            output = "3";
                        }
                    }
                    break;
                case 4:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "4";
                        }
                        else if (input.equals("0")) {
                            output = "4";
                        }
                        else if (input.equals("-0")) {
                            output = "-4";
                        }
                        if (displayReset) {
                            output = "4";
                        }
                    }
                    break;
                case 5:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "5";
                        }
                        else if (input.equals("0")) {
                            output = "5";
                        }
                        else if (input.equals("-0")) {
                            output = "-5";
                        }
                        if (displayReset) {
                            output = "5";
                        }
                    }
                    break;
                case 6:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "6";
                        }
                        else if (input.equals("0")) {
                            output = "6";
                        }
                        else if (input.equals("-0")) {
                            output = "-6";
                        }
                        if (displayReset) {
                            output = "6";
                        }
                    }
                    break;
                case 7:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "7";
                        }
                        else if (input.equals("0")) {
                            output = "7";
                        }
                        else if (input.equals("-0")) {
                            output = "-7";
                        }
                        if (displayReset) {
                            output = "7";
                        }
                    }
                    break;
                case 8:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "8";
                        }
                        else if (input.equals("0")) {
                            output = "8";
                        }
                        else if (input.equals("-0")) {
                            output = "-8";
                        }
                        if (displayReset) {
                            output = "8";
                        }
                    }
                    break;
                case 9:
                    if (digit < MAX_DIGIT) {
                        if (!input.equals("0") && !input.equals("-0")) {
                            output = input + "9";
                        }
                        else if (input.equals("0")) {
                            output = "9";
                        }
                        else if (input.equals("-0")) {
                            output = "-9";
                        }
                        if (displayReset) {
                            output = "9";
                        }
                    }
                    break;
            }

            digit = input.length();
            equalsReset = false;
            displayReset = false;
            displayText(output);
        }

    }

    private double add(String addends1, String addends2) {
        double sum = 0.0;

        try {
            number1 = Double.parseDouble(addends1);
            number2 = Double.parseDouble(addends2);

            sum = number1 + number2;
        }

        catch (Exception ignored) {}

        return sum;
    }

    private double subtract(String minuend, String subtrahend) {
        double difference = 0.0;

        try {
            number1 = Double.parseDouble(minuend);
            number2 = Double.parseDouble(subtrahend);

            difference = number1 - number2;
        }
        catch (Exception ignored) {}

        return difference;
    }

    private double multiply(String multiplicand, String multiplier) {
        double product = 0.0;

        try {
            number1 = Double.parseDouble(multiplicand);
            number2 = Double.parseDouble(multiplier);

            product = number1 * number2;
        }
        catch (Exception ignored) {}

        return product;
    }

    private double divide(String dividend, String divisor) {
        double quotient = 0.0;

        try {
            number1 = Double.parseDouble(dividend);
            number2 = Double.parseDouble(divisor);

            quotient = number1 / number2;
        }
        catch (Exception ignored) {}

        return quotient;
    }

    private double calculate(String number1, String number2, String operator) {
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

    private boolean checkForDecimal() {
        input = textViewDisplay.getText().toString();
        digit = input.length();

        for (int i = 0; i < digit; i++) {
            if (String.valueOf(input.charAt(i)).equals(".")) {
                return true;
            }
        }

        return false;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void displayText(String display) {
        double number = 0.0;

        try {
            number = Double.parseDouble(display);
        }
        catch (Exception ignored) {}

        if (display.length() >= 3 && display.length() <= MAX_DIGIT) {
            int i = display.length() - 1;

            if (display.charAt(i) == '0' && display.charAt(i-1) == '.') {
                if (displayReset) {
                    textViewDisplay.setText(String.format("%.0f", number));
                }
                else {
                    textViewDisplay.setText(display);
                }

            }
            else if (display.equals("Infinity")) {
                textViewDisplay.setText("NaN");
            }
            else {
                textViewDisplay.setText(display);
            }
        }

        else if (display.length() > MAX_DIGIT) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < MAX_DIGIT - 3; i++) {
                sb.append(display.charAt(i));
            }

            int j = 0;
            while (true) {
                if (display.charAt(j++) == 'E') {
                    sb.append('E');
                    for (int i = j; i < display.length(); i++) {
                        sb.append(display.charAt(i));
                    }
                    break;
                }
            }
            textViewDisplay.setText(sb.toString());
        }

        else {
            textViewDisplay.setText(display);
        }
    }
}
