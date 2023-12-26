package com.example.smartlab.bussinessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.PatientInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.example.smartlab.businessService.PatientService;
import com.google.android.material.textfield.TextInputEditText;

public class PatientSetPasswordActivity extends AppCompatActivity {

    TextInputEditText inputPassword, inputRePassword;
    Button buttonAcceptCreatePassword;
    PatientInfo patientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_set_password);

        InitVariable();
        setEventListener();
    }

    private void InitVariable() {
        inputPassword = findViewById(R.id.input_Password);
        inputRePassword = findViewById(R.id.input_RePassword);

        buttonAcceptCreatePassword = findViewById(R.id.button_AcceptCreatePassword);

        patientInfo = (PatientInfo) getIntent().getSerializableExtra("patientInfo");
    }

    private void setEventListener() {
        try {
            buttonAcceptCreatePassword.setOnClickListener(event -> {
                String password = inputPassword.getText().toString().trim();
                String rePassword = inputRePassword.getText().toString().trim();

                if (password.isEmpty()) {
                    inputPassword.setError("Vui lòng nhập mật khẩu !");
                    inputPassword.requestFocus();
                    return;
                } else if (!password.equals(rePassword)) {
                    inputRePassword.setError("Mật khẩu không trùng khớp !");
                    inputRePassword.requestFocus();
                    return;
                }

                patientInfo.setPassword(password);

                Log.d("QUANG password insert | ", patientInfo.getPassword());

                PatientService.getInstance().Insert(patientInfo).thenAccept(referenceInfo -> {
                    if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
                        Log.d("QUANG password inserted | ", patientInfo.getPassword());

                        Intent intent = new Intent(getApplicationContext(), PatientUpdateInfoActivity.class);
                        intent.putExtra("patientInfo", patientInfo);
                        startActivity(intent);
                    } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
                        Toast.makeText(PatientSetPasswordActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).exceptionally(ex -> {
                    Toast.makeText(PatientSetPasswordActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
            });
        }
        catch (Exception ex) {
            Toast.makeText(PatientSetPasswordActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}