package com.example.smartlab.bussinessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.smartlab.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class PatientUpdateInfoActivity extends AppCompatActivity {

    TextInputEditText inputPatientName, inputDateOfBirth, inputAddress;
    RadioButton rbMale, rbFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_update_info);

        InitVariable();
        setEventListener();
    }

    private void InitVariable() {
        inputPatientName = findViewById(R.id.input_PatientName);
        inputDateOfBirth = findViewById(R.id.input_PatientDateOfBirth);
        inputAddress = findViewById(R.id.input_PatientAddress);

        rbMale = findViewById(R.id.radioButton_male);
        rbFemale = findViewById(R.id.radioButton_female);
    }

    private void setEventListener() {
        inputDateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            PatientUpdateInfoActivity.this,
                            (view, selectedYear, selectedMonth, selectedDay) -> {
                                String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                inputDateOfBirth.setText(selectedDate); // Đặt giá trị ngày đã chọn vào EditText
                            },
                            year, month, day
                    );

                    datePickerDialog.show();
                }
            }
        });

    }
}