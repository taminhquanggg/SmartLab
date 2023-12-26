package com.example.smartlab.bussinessView.businessActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.PatientInfo;
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
    PatientInfo patientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_get_otp);

        InitVariable();
        setEventListener();
    }

    private void InitVariable() {
        patientInfo = new PatientInfo(getIntent().getSerializableExtra("patientInfo"));
        inputPhoneNumber = findViewById(R.id.input_PhoneNumber);
        buttonGetOTP = findViewById(R.id.button_GetOTP);
        progressBar = findViewById(R.id.progressBar);
        countryCodePicker = findViewById(R.id.countryCodePicker);
    }

    private void setEventListener() {
        countryCodePicker.registerCarrierNumberEditText(inputPhoneNumber);

        buttonGetOTP.setOnClickListener(event -> {
            if (inputPhoneNumber.getText().toString().isEmpty()) {
                inputPhoneNumber.setError("Vui lòng nhập số điện thoại !");
                inputPhoneNumber.requestFocus();
                return;
            } else if (!countryCodePicker.isValidFullNumber()) {
                inputPhoneNumber.setError("Số điện thoại không tồn tại !");
                inputPhoneNumber.requestFocus();
                return;
            }
            patientInfo.setPhoneNumber(countryCodePicker.getFullNumberWithPlus());

            InProgress(true);

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
                                    Log.e("QUANG", "FirebaseException:" + e.getMessage());
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
}