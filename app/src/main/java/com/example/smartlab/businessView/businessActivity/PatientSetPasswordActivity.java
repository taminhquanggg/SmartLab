package com.example.smartlab.businessView.businessActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.example.smartlab.businessService.PatientService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PatientSetPasswordActivity extends AppCompatActivity {

    TextInputEditText inputPassword, inputRePassword;
    Button buttonAcceptCreatePassword;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_set_password);
        try {
            InitVariable();
            setEventListener();
        } catch (Exception ex) {
            Log.e("LOG DEBUG PatientSetPasswordActivity| ", ex.getMessage());
            Toast.makeText(PatientSetPasswordActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        inputPassword = findViewById(R.id.input_Password);
        inputRePassword = findViewById(R.id.input_RePassword);

        buttonAcceptCreatePassword = findViewById(R.id.button_AcceptCreatePassword);

        patient = (Patient) getIntent().getSerializableExtra("patientInfo");
    }

    private void setEventListener() {
        buttonAcceptCreatePassword.setOnClickListener(event -> {
            String password = inputPassword.getText().toString();
            String rePassword = inputRePassword.getText().toString();

            if (ValidateInput(password, rePassword)) {
                patient.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
                PatientService.getInstance().Insert(patient)
                        .addOnSuccessListener(patientReferenceInfo -> {
                            if (patientReferenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
                                Intent intent = new Intent(getApplicationContext(), PatientUpdateInfoActivity.class);
                                intent.putExtra("patientInfo", patient);
                                startActivity(intent);
                            } else if (patientReferenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
                                Log.e("LOG DEBUG PatientSetPasswordActivity| ", patientReferenceInfo.getMessage());
                                Toast.makeText(PatientSetPasswordActivity.this, "ERROR: " + patientReferenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("LOG DEBUG PatientSetPasswordActivity| ", e.getMessage());

                            Toast.makeText(PatientSetPasswordActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    private boolean ValidateInput(String password, String rePassword) {
        if (password.isEmpty()) {
            inputPassword.setError("Vui lòng nhập mật khẩu !");
            inputPassword.requestFocus();
            return false;
        } else if (!password.equals(rePassword)) {
            inputRePassword.setError("Mật khẩu không trùng khớp !");
            inputRePassword.requestFocus();
            return false;
        }
        return true;
    }
}