package com.projetandroid.projetcalculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    TextView txtResult;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnPlus, btnMinus, btnProd, btnDiv;
    Button btnDot, btnClear, btnEqual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnProd = findViewById(R.id.btnProd);
        btnDiv = findViewById(R.id.btnDiv);

        btnDot = findViewById(R.id.btnDot);
        btnClear = findViewById(R.id.btnC);
        btnEqual = findViewById(R.id.btnEqual);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number("9");
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sign("+");
                btnDot.setClickable(true);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sign("-");
                btnDot.setClickable(true);
            }
        });

        btnProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sign("*");
                btnDot.setClickable(true);
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sign("/");
                btnDot.setClickable(true);
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDot();
                btnDot.setClickable(false);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText("");
                btnDot.setClickable(true);
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluateExpression();
                btnDot.setClickable(true);
            }
        });
    }

    private void AddDot() {
        String temp = txtResult.getText().toString();
        boolean tempEnd = temp.endsWith("+") || temp.endsWith("-") || temp.endsWith("*") || temp.endsWith("/");
        if(temp.length() > 0 && tempEnd){
            txtResult.setText(temp + "0.");
        } else if (temp.length() == 0) {
            txtResult.setText(temp + "0.");
        } else if (temp.endsWith(".")) {
            txtResult.setText(temp);
        }else {
            Number(".");
        }
    }

    public void Number(String number){
        String temp = txtResult.getText().toString();
        txtResult.setText(temp + number);
    }

    public void Sign(String sign) {
        String temp = txtResult.getText().toString();
        if (temp.length() > 0) {
            if (temp.endsWith("+") || temp.endsWith("-") || temp.endsWith("*") || temp.endsWith("/")) {
                // Replace the last character with the parameter 'sign'
                String modifiedString = temp.substring(0, temp.length() - 1) + sign;
                txtResult.setText(modifiedString);
            }
            else {
                txtResult.setText(temp + sign);
            }
        } else {
            // Display a Toast message indicating that the string is empty
            Toast.makeText(this, "Invalid format used.", Toast.LENGTH_SHORT).show();
        }
    }

    private void evaluateExpression() {
        try {
            String expressionStr = txtResult.getText().toString();
            Expression expression = new ExpressionBuilder(expressionStr).build();
            double result = expression.evaluate();

            // Check if there are non-zero digits after the decimal point
            if (hasNonZeroDecimal(result)) {
                txtResult.setText(String.valueOf(result));
            } else {
                // If no non-zero digits after the decimal point, convert to integer
                txtResult.setText(String.valueOf((int) result));
            }
        } catch (ArithmeticException e) {
            // Handle division by zero or other arithmetic errors
            // Handle division by zero
            if (e.getMessage().contains("by zero")) {
                Toast.makeText(this, "Can't divide by zero", Toast.LENGTH_SHORT).show();
            } else {
                // Handle other arithmetic errors
                Toast.makeText(this, "Error in expression", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // Handle other types of errors
            // Toast.makeText(this, "Invalid expression", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasNonZeroDecimal(double value) {
        // Convert the double to a string
        String valueStr = String.valueOf(value);

        // Check if there are non-zero digits after the decimal point
        int dotIndex = valueStr.indexOf('.');
        if (dotIndex != -1 && dotIndex < valueStr.length() - 1) {
            String decimalPart = valueStr.substring(dotIndex + 1);
            return decimalPart.matches(".*[1-9].*");
        }

        return false;
    }

}
