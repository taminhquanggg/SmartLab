package com.example.smartlab.bussinessView.businessActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.PatientInfo;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.example.smartlab.businessService.PatientService;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PatientVerifyOTPActivity extends AppCompatActivity {

    private EditText inputOtpCode1, inputOtpCode2, inputOtpCode3, inputOtpCode4, inputOtpCode5, inputOtpCode6;
    private TextView textMobilePhone;
    private Button buttonVerifyOTP, buttonResendOTP;
    private SpinKitView progressBar;
    private PatientInfo patientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_verify_otp);

        InitVariable();
        setTextMobilePhone();
        setEventInputOtp();
        setEventListener();
    }

    private void InitVariable() {
        inputOtpCode1 = findViewById(R.id.input_otpCode1);
        inputOtpCode2 = findViewById(R.id.input_otpCode2);
        inputOtpCode3 = findViewById(R.id.input_otpCode3);
        inputOtpCode4 = findViewById(R.id.input_otpCode4);
        inputOtpCode5 = findViewById(R.id.input_otpCode5);
        inputOtpCode6 = findViewById(R.id.input_otpCode6);

        textMobilePhone = findViewById(R.id.txt_mobilePhone);
        buttonVerifyOTP = findViewById(R.id.button_Verify);
        buttonResendOTP = findViewById(R.id.button_reSendOTP);

        progressBar = findViewById(R.id.progressBarVerify);

        patientInfo = (PatientInfo) getIntent().getSerializableExtra("patientInfo");
    }

    private void setEventListener() {
        buttonVerifyOTP.setOnClickListener(event -> {
            if (inputOtpCode1.getText().toString().trim().isEmpty() || inputOtpCode2.getText().toString().trim().isEmpty() || inputOtpCode3.getText().toString().trim().isEmpty() || inputOtpCode4.getText().toString().trim().isEmpty() || inputOtpCode5.getText().toString().trim().isEmpty() || inputOtpCode6.getText().toString().trim().isEmpty()) {
                Toast.makeText(PatientVerifyOTPActivity.this, "Hãy nhập đầy đủ OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            String strOtpCode = inputOtpCode1.getText().toString() + inputOtpCode2.getText().toString() + inputOtpCode3.getText().toString() + inputOtpCode4.getText().toString() + inputOtpCode5.getText().toString() + inputOtpCode6.getText().toString();

            if (patientInfo.getVerificationId() != null) {
                InProgress(true);
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(patientInfo.getVerificationId(), strOtpCode);

                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            PatientService.getInstance().isPhoneNumberExisted(patientInfo.getPhoneNumber())
                                    .thenAccept(referenceInfo -> {
                                        if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
                                            PatientInfo patientInfoExist = referenceInfo.getData();
                                            if (patientInfoExist != null) {
                                                // Xử lý khi có dữ liệu
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(), PatientSetPasswordActivity.class);
                                                intent.putExtra("patientInfo", patientInfo);
                                                startActivity(intent);
                                            }
                                        } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
                                            Toast.makeText(PatientVerifyOTPActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .exceptionally(ex -> {
                                        Toast.makeText(PatientVerifyOTPActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                                        return null;
                                    });

                            InProgress(false);
                        } else {
                            Toast.makeText(PatientVerifyOTPActivity.this, "OTP không đúng, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        buttonResendOTP.setOnClickListener(event -> {
            InProgress(true);
            PhoneAuthOptions phoneAuthProvider = PhoneAuthOptions.newBuilder().setPhoneNumber(textMobilePhone.getText().toString()).setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(PatientVerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    InProgress(false);
                }

                @Override
                public void onCodeSent(@NonNull String reSendVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    patientInfo.setVerificationId(reSendVerificationId);
                    Toast.makeText(PatientVerifyOTPActivity.this, "OTP đã được gửi lại", Toast.LENGTH_SHORT).show();
                    InProgress(false);
                }
            }).build();
            PhoneAuthProvider.verifyPhoneNumber(phoneAuthProvider);
        });
    }

    private void setTextMobilePhone() {
        textMobilePhone.setText(patientInfo.getPhoneNumber());
    }

    private void setEventInputOtp() {
        inputOtpCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputOtpCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputOtpCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputOtpCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputOtpCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void InProgress(Boolean request) {
        if (request) {
            buttonVerifyOTP.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            buttonVerifyOTP.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}