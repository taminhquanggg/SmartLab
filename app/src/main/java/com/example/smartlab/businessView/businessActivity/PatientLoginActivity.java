package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Patient;
import com.google.android.material.textfield.TextInputEditText;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PatientLoginActivity extends AppCompatActivity {

    Button buttonLogin;
    TextInputEditText inputPasswordLogin;

    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        try {
            InitVariable();
            setEventListener();
        } catch (Exception ex) {
            Log.e("ERROR", "PatientLoginActivity|" + ex.getMessage());
            Toast.makeText(PatientLoginActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        buttonLogin = findViewById(R.id.button_PatientLogin);
        inputPasswordLogin = findViewById(R.id.input_PasswordLogin);
        patient = (Patient) getIntent().getSerializableExtra("patientInfo");
    }

    private void setEventListener() {
        buttonLogin.setOnClickListener(event -> {

            if (ValidateInput()) {
                Patient patientLogin = new Patient();
                patientLogin.setPassword(inputPasswordLogin.getText().toString());

                BCrypt.Result result = BCrypt.verifyer().verify(patientLogin.getPassword().toCharArray(), patient.getPassword());

                if (result.verified) {
                    Intent intent = new Intent(getApplicationContext(), PatientHomeActivity.class);
                    intent.putExtra("patientInfo", patient);
                    startActivity(intent);
                } else {
                    inputPasswordLogin.setError("Mật khẩu sai, vui lòng nhập lại !");
                    inputPasswordLogin.requestFocus();
                }
            }
        });
    }

    private boolean ValidateInput() {
        if (inputPasswordLogin.getText().toString().isEmpty()) {
            inputPasswordLogin.setError("Vui lòng nhập mật khẩu !");
            inputPasswordLogin.requestFocus();
            return false;
        }
        return true;
    }
}