package com.example.smartlab.businessView.businessActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.example.smartlab.businessService.PatientService;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.FirebaseException;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class PatientGetOTPActivity extends AppCompatActivity {

    EditText inputPhoneNumber;
    Button buttonGetOTP;
    SpinKitView progressBar;
    CountryCodePicker countryCodePicker;
    Patient patientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_get_otp);

        try {
            InitVariable();
            setEventListener();
        } catch (Exception ex) {
            Log.e("ERROR", "PatientGetOTPActivity|" + ex.getMessage());
            Toast.makeText(PatientGetOTPActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        patientInfo = new Patient(getIntent().getSerializableExtra("patientInfo"));
        inputPhoneNumber = findViewById(R.id.input_PhoneNumber);
        buttonGetOTP = findViewById(R.id.button_GetOTP);
        progressBar = findViewById(R.id.progressBar);
        countryCodePicker = findViewById(R.id.countryCodePicker);
    }

    private void setEventListener() {

        buttonGetOTP.setOnClickListener(event -> {
            handlePhoneNumberExistenceCheck();
        });

        inputPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    handlePhoneNumberExistenceCheck();
                }
                return false;
            }
        });
    }

    private void InProgress(Boolean request) {
        if (request) {
            buttonGetOTP.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            buttonGetOTP.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private boolean ValidateInput() {
        countryCodePicker.registerCarrierNumberEditText(inputPhoneNumber);

        if (inputPhoneNumber.getText().toString().isEmpty()) {
            inputPhoneNumber.setError("Vui lòng nhập số điện thoại !");
            inputPhoneNumber.requestFocus();
            return false;
        } else if (!countryCodePicker.isValidFullNumber()) {
            inputPhoneNumber.setError("Số điện thoại không tồn tại !");
            inputPhoneNumber.requestFocus();
            return false;
        }
        return true;
    }

    private void handlePhoneNumberExistenceCheck() {
        if (!ValidateInput()) {
            return;
        }

        patientInfo.setPhoneNumber(countryCodePicker.getFullNumberWithPlus());

        InProgress(true);

        PatientService.getInstance().isPhoneNumberExisted(patientInfo.getPhoneNumber())
                .addOnSuccessListener(patientReferenceInfo -> {
                            if (patientReferenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
                                Patient patientExist = patientReferenceInfo.getData();
                                if (patientExist != null) {
                                    patientInfo = patientExist;
                                    Intent intent = new Intent(getApplicationContext(), PatientLoginActivity.class);
                                    intent.putExtra("patientInfo", patientInfo);
                                    startActivity(intent);
                                } else {
                                    handleOnGetOTP();
                                }
                            } else if (patientReferenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
                                Log.e("ERROR", "PatientGetOTPActivity|" + patientReferenceInfo.getMessage());
                                Toast.makeText(PatientGetOTPActivity.this, "ERROR: " + patientReferenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ).addOnFailureListener(e -> {
                    Log.e("ERROR", "PatientGetOTPActivity|" + e.getMessage());
                    Toast.makeText(PatientGetOTPActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void handleOnGetOTP() {
        //verify phone number
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder()
                        .setPhoneNumber(patientInfo.getPhoneNumber())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                inputPhoneNumber.setError(e.getMessage());
                                Toast.makeText(PatientGetOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("ERROR", "PhoneAuthOptions|FirebaseException:" + e.getMessage());
                                InProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                //super.onCodeSent(s, forceResendingToken);
                                patientInfo.setVerificationId(verificationId);
                                //action
                                Intent intent = new Intent(getApplicationContext(), PatientVerifyOTPActivity.class);
                                intent.putExtra("patientInfo", patientInfo);
                                startActivity(intent);
                                InProgress(false);
                            }
                        }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}