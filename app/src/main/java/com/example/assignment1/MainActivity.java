package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Calculator calc = new Calculator();
    private ArrayList<Button> numberButtons = new ArrayList<>();
    private ArrayList<Button> operatorButtons = new ArrayList<>();
    private ArrayList<Button> specialButtons = new ArrayList<>();
    private String input, output, num1, num2, operator, operatorBuffer;
    private final int MAX_DIGIT = 14;
    private int digit;
    private double result;
    private boolean decimal, which, operatorsPressed, equalsReset, displayReset;

    private TextView textViewDisplay;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        textViewDisplay = (TextView)findViewById(R.id.textViewDisplay);

        for (int i = 0; i < 4; i++) {
            String[] operators = new String[] { "+", "-", "x", "/" };

            switch (i) {
                case 0:
                    operatorButtons.add((Button)findViewById(R.id.btnAdd));
                    break;
                case 1:
                    operatorButtons.add((Button)findViewById(R.id.btnMinus));
                    break;
                case 2:
                    operatorButtons.add((Button)findViewById(R.id.btnMultiply));
                    break;
                case 3:
                    operatorButtons.add((Button)findViewById(R.id.btnDivide));
                    break;
            }

            operatorButtons.get(i).setOnClickListener(new OperatorButtonClicked(operators[i]));
        }

        for (int i = 0; i < 5; i++) {
            String[] specials = new String[] {"Clear", "Back", "PlusOrMinus", "Decimal", "Equals"};

            switch (i) {
                case 0:
                    specialButtons.add((Button)findViewById(R.id.btnClear));
                    break;
                case 1:
                    specialButtons.add((Button)findViewById(R.id.btnBack));
                    break;
                case 2:
                    specialButtons.add((Button)findViewById(R.id.btnPlusOrMinus));
                    break;
                case 3:
                    specialButtons.add((Button)findViewById(R.id.btnDecimal));
                    break;
                case 4:
                    specialButtons.add((Button)findViewById(R.id.btnEquals));
                    break;
            }

            specialButtons.get(i).setOnClickListener(new SpecialButtonClicked(specials[i]));
        }

        for (int i = 0; i < 10; i++) {
            switch (i) {
                case 0:
                    numberButtons.add((Button)findViewById(R.id.btnZero));
                    break;
                case 1:
                    numberButtons.add((Button)findViewById(R.id.btnOne));
                    break;
                case 2:
                    numberButtons.add((Button)findViewById(R.id.btnTwo));
                    break;
                case 3:
                    numberButtons.add((Button)findViewById(R.id.btnThree));
                    break;
                case 4:
                    numberButtons.add((Button)findViewById(R.id.btnFour));
                    break;
                case 5:
                    numberButtons.add((Button)findViewById(R.id.btnFive));
                    break;
                case 6:
                    numberButtons.add((Button)findViewById(R.id.btnSix));
                    break;
                case 7:
                    numberButtons.add((Button)findViewById(R.id.btnSeven));
                    break;
                case 8:
                    numberButtons.add((Button)findViewById(R.id.btnEight));
                    break;
                case 9:
                    numberButtons.add((Button)findViewById(R.id.btnNine));
                    break;
            }
            numberButtons.get(i).setOnClickListener(new NumberButtonClicked(i));
        }

    }

    class OperatorButtonClicked implements View.OnClickListener {
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
                    result = calc.calculate(num1, num2, operator);
                    num2 = String.valueOf(result);
                }

                else {
                    num1 = textViewDisplay.getText().toString();
                    operator = oper;

                    if (operatorBuffer != null) {
                        result = calc.calculate(num2, num1, operatorBuffer);
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

    class SpecialButtonClicked implements View.OnClickListener {
        private String button;

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
                            result = calc.calculate(num1, num2, operator);
                            num2 = String.valueOf(result);
                        } else {
                            num1 = textViewDisplay.getText().toString();

                            if (operatorBuffer != null) {
                                result = calc.calculate(num2, num1, operatorBuffer);
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

        public SpecialButtonClicked(String button) {
            this.button = button;
        }
    }

    class NumberButtonClicked implements View.OnClickListener {
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

    @SuppressLint("DefaultLocale")
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

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

}