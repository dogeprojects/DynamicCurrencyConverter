package com.DynamicCurrencyConverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static java.lang.Float.valueOf;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerFrom, spinnerTo;
    ArrayAdapter<String> currencyFromArray, currencyToArray;
    //Button button;
    float coefTo = 1f;
    float contentFromEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editText);
        //editText.setOnClickListener(this);

        editText.addTextChangedListener(new OwnWatcher());

        //button = (Button) findViewById(R.id.button);
        //implementing OnClickListener (need to override the method)

        spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
        //implementing OnItemSelectedListener (need to override the method)
        spinnerFrom.setOnItemSelectedListener(this);

        currencyFromArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        currencyFromArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(currencyFromArray);

        currencyFromArray.add("RUB");
        currencyFromArray.add("USD");
        currencyFromArray.add("EUR");
        currencyFromArray.add("JPY");
        currencyFromArray.setNotifyOnChange(true);

        spinnerFrom.setSelection(0);

        spinnerTo = (Spinner) findViewById(R.id.spinnerTo);
        //implementing OnItemSelectedListener (need to override the method)
        spinnerTo.setOnItemSelectedListener(this);

        currencyToArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        currencyToArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTo.setAdapter(currencyToArray);

        //смотрим изменилось ли что-либо в editText и, если изменилось,
        //то берём коэффициент из выбранного ToSpinnerPosition и FromSpinnerPosition
        //и выводим результат в resultView, умножая пользовательское значение
        //на соответствующий коэф: contentFromEditText*coefTo

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                TextView resultView = (TextView) findViewById(R.id.resultView);

                if (editText.getText().toString().isEmpty()
                        || editText.getText().toString().equals(".")) {
                    resultView.setText("0");
                    return;
                }
                contentFromEditText = valueOf(editText.getText().toString());

                String fromSpinnerPosition = spinnerFrom.getSelectedItem().toString();
                String toSpinnerPosition = spinnerTo.getSelectedItem().toString();
                switch (fromSpinnerPosition){
                    case "RUB":
                        if (toSpinnerPosition == "USD") {
                            coefTo = 0.015f;
                        } else if (toSpinnerPosition == "EUR") {
                            coefTo = 0.015f;
                        } else if (toSpinnerPosition == "JPY") {
                            coefTo = 1.66f;
                        }
                        break;
                    case "USD":
                        if (toSpinnerPosition == "RUB") {
                            coefTo = 65.92f;
                        } else if (toSpinnerPosition == "EUR") {
                            coefTo = 0.87f;
                        } else if (toSpinnerPosition == "JPY") {
                            coefTo = 109.43f;
                        }
                        break;
                    case "EUR":
                        if (toSpinnerPosition == "RUB") {
                            coefTo = 75.39f;
                        } else if (toSpinnerPosition == "USD") {
                            coefTo = 1.14f;
                        } else if (toSpinnerPosition == "JPY") {
                            coefTo = 125.18f;
                        }
                        break;
                    case "JPY":
                        if (toSpinnerPosition == "RUB") {
                            coefTo = 0.60f;
                        } else if (toSpinnerPosition == "USD") {
                            coefTo = 0.0091f;
                        } else if (toSpinnerPosition == "EUR") {
                            coefTo = 0.008f;
                        }
                        break;
                }
                resultView.setText(String.format ("%.2f", contentFromEditText) + " " + fromSpinnerPosition + " -> "
                        + String.format("%.2f", contentFromEditText*coefTo) + " " + toSpinnerPosition);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //first spinner item position
        int fromSpinnerPosition = spinnerFrom.getSelectedItemPosition();
        switch (fromSpinnerPosition){
            case 0: //1st item of 1st spinner selected
                //Fill data for second spinner
                fillRUB();
                break;
            case 1: //2nd item of 1st spinner selected
                //fill data for second spinner
                fillUSD();
                break;
            case 2:
                fillEUR();
                break;
            case 3:
                fillJPY();
        }
    }

    private void fillRUB() {
        currencyToArray.clear();
        currencyToArray.add("USD");
        currencyToArray.add("EUR");
        currencyToArray.add("JPY");
        currencyToArray.notifyDataSetChanged();
    }

    private void fillUSD() {
        currencyToArray.clear();
        currencyToArray.add("RUB");
        currencyToArray.add("EUR");
        currencyToArray.add("JPY");
        currencyToArray.notifyDataSetChanged();
    }

    private void fillEUR() {
        currencyToArray.clear();
        currencyToArray.add("RUB");
        currencyToArray.add("USD");
        currencyToArray.add("JPY");
        currencyToArray.notifyDataSetChanged();
    }

    private void fillJPY(){
        currencyToArray.clear();
        currencyToArray.add("RUB");
        currencyToArray.add("USD");
        currencyToArray.add("EUR");
        currencyToArray.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public class TextChangedWatcher {

    }

    //The division level
    public class OwnWatcher implements TextWatcher {

        private static final char space = ' ';

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Remove spacing char
            if (s.length() > 0 && (s.length() % 4) == 0) {
                final char c = s.charAt(s.length() - 1);
                if (space == c) {
                    s.delete(s.length() - 1, s.length());
                }
            }
            // Insert char where needed
            if (s.length() > 0 && (s.length() % 4) == 0) {
                char c = s.charAt(s.length() - 1);
                // Only if its a digit where there should be a space we insert a space
                if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                    s.insert(s.length() - 1, String.valueOf(space));
                }
            }
        }
    }
}
