package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    double value = 0.00;
    boolean isPos = true;
    boolean decimalPressed = false;
    boolean equalsPressed = false;
    double holdingValue = 0.00;
    String operationSymbol = "+";
    TextView resultText;
    TextView sumText;
    String solutionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button decimalButton = findViewById(R.id.buttonDec);
        decimalButton.setOnClickListener(view -> onDecimalButtonClick());

        resultText = findViewById(R.id.result);
        sumText = findViewById(R.id.solutionText);

        for (int i = 0; i <= 9; i++) {
            int digit = i;
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            button.setOnClickListener(view -> onNumberButtonClick(digit));
        }
    }
    private void onDecimalButtonClick() {
        // Toggle the decimalPressed flag when the decimal button is clicked
        decimalPressed = !decimalPressed;

        // You might want to update your UI here to reflect the state of the decimalPressed flag
        updateUI(resultText);
    }
    private void onNumberButtonClick(int digit) {
        if (holdingValue == value){
            holdingValue = 0;
        }
        if (holdingValue == 0) {
            holdingValue = digit;
        } else {
            // Multiply the current holdingValue by 10 and then add the digit
            holdingValue = holdingValue * 10 + digit;
        }
        System.out.println("Updated value: " + holdingValue);
        //TextView resultText = findViewById(R.id.result);
        updateUI(resultText);
    }
    private void updateUI(TextView textView) {
        // Format the holdingValue based on the decimalPressed flag
//        boolean isWholeNum = true;
//        if(holdingValue == (int)holdingValue){
//            isWholeNum = true;
//        }
//        else{
//            isWholeNum = false;
//        }
//        if (decimalPressed || isWholeNum == false) {
        if (decimalPressed || holdingValue % 1 != 0) {
            // Show decimals if the decimal button is pressed or if there are more than 1 decimal places
            textView.setText(String.format("%.2f", holdingValue));
        } else {
            // Show as an integer if the decimal button is not pressed and there's only 1 or 0 decimal places
            textView.setText(String.valueOf((int) holdingValue));
        }
        if (value != 0 && !equalsPressed) {
            solutionText = value + " " + operationSymbol;
        }
        sumText.setText(solutionText);
    }
    private void calculation(){
        if (value != 0) {
            if ("+".equals(operationSymbol)) {
                value = value + holdingValue;
            } else if ("-".equals(operationSymbol)) {
                value = value - holdingValue;
            } else if ("*".equals(operationSymbol)) {
                value = value * holdingValue;
            } else if ("/".equals(operationSymbol)) {
                if (holdingValue != 0) {
                    value = value / holdingValue;
                } else {
                    Toast.makeText(this, "Error, problem dividing", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            value = holdingValue;
        }

        holdingValue = value;
    }
    public void clear(View v){
        holdingValue = 0;
        value = 0;
        solutionText = "";
        updateUI(resultText);
    }
    public void delete(View v){
        double lastDigit = holdingValue % 10;
        holdingValue = holdingValue - lastDigit;
        holdingValue = holdingValue / 10;
        updateUI(resultText);
    }
    public void add(View v){
        calculation();
        operationSymbol = "+";
        updateUI(resultText);
    }
    public void subtract(View v){
        calculation();
        operationSymbol = "-";
        updateUI(resultText);
    }
    public void multiply(View v){
        calculation();
        operationSymbol = "*";
        updateUI(resultText);
    }
    public void divide(View v){
        calculation();
//        operationSymbol = "/";
        if (holdingValue != 0) {
            operationSymbol = "/";
        } else {
            // Handle division by zero, for example, show an error message.
            Toast.makeText(this, "Error, division by zero", Toast.LENGTH_SHORT).show();
        }
        updateUI(resultText);
    }
    public void sum(View v){
        equalsPressed = true;
        double currTotal = value;
        double currCarry = holdingValue;
        calculation();
        solutionText = String.valueOf(currTotal) + " " + operationSymbol + " " + currCarry + " = ";
        updateUI(resultText);
        value = 0;
        holdingValue = 0;
        equalsPressed = false;
    }
    public void sign(View v){
        if(isPos == true){
         holdingValue = -holdingValue;
         isPos = false;
        }
        else{
            holdingValue = -holdingValue;
            isPos = true;
        }
        updateUI(resultText);
    }
}