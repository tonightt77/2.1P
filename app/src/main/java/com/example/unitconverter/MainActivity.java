package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;



public class MainActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    private Spinner sourceUnitSpinner;
    private Spinner targetUnitSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.Converter_App);
        }

        categorySpinner = findViewById(R.id.category_spinner);
        sourceUnitSpinner = findViewById(R.id.source_unit_spinner);
        targetUnitSpinner = findViewById(R.id.target_unit_spinner);

        String[] categories = new String[]{"Length", "Weight", "Temperature"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                ArrayAdapter<CharSequence> adapter;

                switch (selectedCategory) {
                    case "Length":
                        adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.length_units, android.R.layout.simple_spinner_item);
                        break;
                    case "Weight":
                        adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.weight_units, android.R.layout.simple_spinner_item);
                        break;
                    case "Temperature":
                        adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.temperature_units, android.R.layout.simple_spinner_item);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + selectedCategory);
                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sourceUnitSpinner.setAdapter(adapter);
                targetUnitSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        EditText inputValue = findViewById(R.id.input_value);
        Button convertButton = findViewById(R.id.convert_button);
        TextView convertedValue = findViewById(R.id.converted_value);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
                String targetUnit = targetUnitSpinner.getSelectedItem().toString();
                String input = inputValue.getText().toString();

                if (input.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a valid value.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double inputValue = Double.parseDouble(input);
                double result = convert(sourceUnit, targetUnit, inputValue);
                String resultMsg = String.format("Conversion result: %.2f %s", result, targetUnit);
                convertedValue.setText(resultMsg);
            }
        });



    }

    private double convertLength(String sourceUnit, String targetUnit, double inputValue) {
        double result = 0;

        // Convert the input value to centimeters
        switch (sourceUnit) {
            case "inch":
                result = inputValue * 2.54;
                break;
            case "foot":
                result = inputValue * 30.48;
                break;
            case "yard":
                result = inputValue * 91.44;
                break;
            case "mile":
                result = inputValue * 160934;
                break;
        }

        // Convert source unit to the target unit
        switch (targetUnit) {
            case "inch":
                result /= 2.54;
                break;
            case "foot":
                result /= 30.48;
                break;
            case "yard":
                result /= 91.44;
                break;
            case "mile":
                result /= 160934;
                break;
        }

        return result;
    }

    private double convertWeight(String sourceUnit, String targetUnit, double inputValue) {
        double result = 0;

        // Convert the input value to grams
        switch (sourceUnit) {
            case "pound":
                result = inputValue * 453.592;
                break;
            case "ounce":
                result = inputValue * 28.3495;
                break;
            case "ton":
                result = inputValue * 907185;
                break;
        }

        // Convert source unit to the target unit
        switch (targetUnit) {
            case "pound":
                result /= 453.592;
                break;
            case "ounce":
                result /= 28.3495;
                break;
            case "ton":
                result /= 907185;
                break;
        }

        return result;
    }

    private double convertTemperature(String sourceUnit, String targetUnit, double inputValue) {
        double result = 0;

        // Convert the input value to Celsius
        switch (sourceUnit) {
            case "Fahrenheit":
                result = (inputValue - 32) / 1.8;
                break;
            case "Kelvin":
                result = inputValue - 273.15;
                break;
            case "Celsius":
                result = inputValue;
                break;
        }

        // Convert the source unit to the target unit
        switch (targetUnit) {
            case "Fahrenheit":
                result = (result * 1.8) + 32;
                break;
            case "Kelvin":
                result += 273.15;
                break;
            case "Celsius":
                break;
        }

        return result;
    }

    private double convert(String sourceUnit, String targetUnit, double inputValue) {
        if (Arrays.asList("inch", "foot", "yard", "mile").contains(sourceUnit)) {
            return convertLength(sourceUnit, targetUnit, inputValue);
        } else if (Arrays.asList("pound", "ounce", "ton").contains(sourceUnit)) {
            return convertWeight(sourceUnit, targetUnit, inputValue);
        } else {
            return convertTemperature(sourceUnit, targetUnit, inputValue);
        }
    }


}