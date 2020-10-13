package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Button> numberButtons = new ArrayList<>();
    private ArrayList<Button> operatorButtons = new ArrayList<>();
    private ArrayList<Button> specialButtons = new ArrayList<>();
    private TextView textViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDisplay = findViewById(R.id.textViewDisplay);

        for (int i = 0; i < 4; i++) {
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
        }

        for (int i = 0; i < 5; i++) {
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
        }
        Calculator calc = new Calculator(numberButtons, operatorButtons, specialButtons, textViewDisplay);
        calc.start();
    }
}