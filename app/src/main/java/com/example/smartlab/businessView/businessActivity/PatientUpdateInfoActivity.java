package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.example.smartlab.businessService.PatientService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class PatientUpdateInfoActivity extends AppCompatActivity {

    TextInputEditText inputPatientName, inputDateOfBirth, inputAddress;
    RadioGroup rgSex;
    RadioButton rbMale, rbFemale;
    Patient patientIntent;
    Button buttonAcceptInfoPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_update_info);
        try {
            InitVariable();
            setEventListener();
        } catch (Exception ex) {
            Log.e("LOG DEBUG PatientUpdateInfoActivity| ", ex.getMessage());
            Toast.makeText(PatientUpdateInfoActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void InitVariable() {
        inputPatientName = findViewById(R.id.input_PatientName);
        inputDateOfBirth = findViewById(R.id.input_PatientDateOfBirth);
        inputAddress = findViewById(R.id.input_PatientAddress);

        rgSex = findViewById(R.id.radio_groupSex);
        rbMale = findViewById(R.id.radioButton_male);
        rbFemale = findViewById(R.id.radioButton_female);

        buttonAcceptInfoPatient = findViewById(R.id.button_AcceptInfoPatient);

        patientIntent = (Patient) getIntent().getSerializableExtra("patientInfo");
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
                                inputDateOfBirth.setText(selectedDate);
                            },
                            year, month, day
                    );

                    datePickerDialog.show();
                }
            }
        });

        buttonAcceptInfoPatient.setOnClickListener(event -> {
            Patient patientInfoUpdate = GetPatientInfoUpdate();
            if (patientInfoUpdate != null) {
                PatientService.getInstance().Update(patientIntent.getPatientID(), patientInfoUpdate)
                        .addOnSuccessListener(patientReferenceInfo -> {
                            if (patientReferenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
                                patientIntent.setPatientName(patientReferenceInfo.getData().getPatientName());
                                patientIntent.setDateOfBirth(patientReferenceInfo.getData().getDateOfBirth());
                                patientIntent.setSex(patientReferenceInfo.getData().getSex());
                                patientIntent.setAddress(patientReferenceInfo.getData().getAddress());

                                Intent intent = new Intent(getApplicationContext(), PatientCongratulationsActivity.class);
                                intent.putExtra("patientInfo", patientIntent);
                                startActivity(intent);
                            } else if (patientReferenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
                                Log.e("LOG DEBUG PatientUpdateInfoActivity| ", patientReferenceInfo.getMessage());
                                Toast.makeText(PatientUpdateInfoActivity.this, "ERROR: " + patientReferenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("LOG DEBUG PatientUpdateInfoActivity| ", e.getMessage());

                            Toast.makeText(PatientUpdateInfoActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    private Patient GetPatientInfoUpdate() {
        if (inputPatientName.getText().toString().isEmpty()) {
            inputPatientName.setError("Vui lòng nhập họ tên !");
            inputPatientName.requestFocus();
            return null;
        } else if (inputDateOfBirth.getText().toString().isEmpty()) {
            inputDateOfBirth.setError("Vui lòng nhập ngày sinh !");
            return null;
        } else if (!rbMale.isChecked() && !rbFemale.isChecked()) {
            rbFemale.setError("Vui lòng chọn giới tính !");
            return null;
        } else if (inputAddress.getText().toString().isEmpty()) {
            inputAddress.setError("Vui lòng nhập địa chỉ !");
            inputAddress.requestFocus();
            return null;
        }

        Patient patientInfoUpdate = new Patient();
        patientInfoUpdate.setPatientName(inputPatientName.getText().toString());
        patientInfoUpdate.setDateOfBirth(inputDateOfBirth.getText().toString());
        patientInfoUpdate.setSex(rbMale.isChecked() ? rbMale.getText().toString() : rbFemale.getText().toString());
        patientInfoUpdate.setAddress(inputAddress.getText().toString());

        return patientInfoUpdate;
    }
}