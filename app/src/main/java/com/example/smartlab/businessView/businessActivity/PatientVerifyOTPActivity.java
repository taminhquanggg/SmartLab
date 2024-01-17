package com.example.smartlab.businessView.businessActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.example.smartlab.businessService.PatientService;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_verify_otp);

        try {
            InitVariable();
            setTextMobilePhone();
            setEventInputOtp();
            setEventListener();
        } catch (Exception ex) {
            Log.e("ERROR", "PatientVerifyOTPActivity|" + ex.getMessage());
            Toast.makeText(PatientVerifyOTPActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

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

        patient = (Patient) getIntent().getSerializableExtra("patientInfo");
    }

    private void setEventListener() {
        buttonVerifyOTP.setOnClickListener(event -> {
            VerifyOTP();
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
                    patient.setVerificationId(reSendVerificationId);
                    Toast.makeText(PatientVerifyOTPActivity.this, "OTP đã được gửi lại", Toast.LENGTH_SHORT).show();
                    InProgress(false);
                }
            }).build();
            PhoneAuthProvider.verifyPhoneNumber(phoneAuthProvider);
        });

        setEventDelOtp(inputOtpCode1, inputOtpCode1);
        setEventDelOtp(inputOtpCode2, inputOtpCode1);
        setEventDelOtp(inputOtpCode3, inputOtpCode2);
        setEventDelOtp(inputOtpCode4, inputOtpCode3);
        setEventDelOtp(inputOtpCode5, inputOtpCode4);
        setEventDelOtp(inputOtpCode6, inputOtpCode5);
    }

    private void setTextMobilePhone() {
        textMobilePhone.setText(patient.getPhoneNumber());
    }

    private void setEventInputOtp() {
        inputOtpCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode2.requestFocus();
                }
            }
        });

        inputOtpCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode3.requestFocus();
                }
            }
        });

        inputOtpCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode4.requestFocus();
                }
            }
        });

        inputOtpCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode5.requestFocus();
                }
            }
        });

        inputOtpCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    inputOtpCode6.requestFocus();
                }
            }
        });

        inputOtpCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                VerifyOTP();
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

    private boolean ValidateInput() {
        if (inputOtpCode1.getText().toString().trim().isEmpty() || inputOtpCode2.getText().toString().trim().isEmpty() || inputOtpCode3.getText().toString().trim().isEmpty() || inputOtpCode4.getText().toString().trim().isEmpty() || inputOtpCode5.getText().toString().trim().isEmpty() || inputOtpCode6.getText().toString().trim().isEmpty()) {
            Toast.makeText(PatientVerifyOTPActivity.this, "Hãy nhập đầy đủ OTP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void VerifyOTP() {
        if (!ValidateInput()) {
            return;
        }

        String strOtpCode = getOTPCodeFromInputs();

        if (patient.getVerificationId() != null) {
            InProgress(true);
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(patient.getVerificationId(), strOtpCode);
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), PatientSetPasswordActivity.class);
                    intent.putExtra("patientInfo", patient);
                    startActivity(intent);
                } else {
                    Toast.makeText(PatientVerifyOTPActivity.this, "OTP không đúng, vui lòng nhập lại", Toast.LENGTH_SHORT).show();

                }
            });
            InProgress(false);
        }
    }

    private String getOTPCodeFromInputs() {
        return inputOtpCode1.getText().toString() +
                inputOtpCode2.getText().toString() +
                inputOtpCode3.getText().toString() +
                inputOtpCode4.getText().toString() +
                inputOtpCode5.getText().toString() +
                inputOtpCode6.getText().toString();
    }

//    private void handlePhoneNumberExistenceCheck() {
//        PatientService.getInstance().isPhoneNumberExisted(patient.getPhoneNumber())
//                .addOnSuccessListener(patientReferenceInfo -> {
//                            if (patientReferenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//                                Patient patientExist = patientReferenceInfo.getData();
//                                if (patientExist != null) {
//                                    patient = patientExist;
//                                    Intent intent = new Intent(getApplicationContext(), PatientLoginActivity.class);
//                                    intent.putExtra("patientInfo", patient);
//                                    startActivity(intent);
//                                } else {
//                                    Intent intent = new Intent(getApplicationContext(), PatientSetPasswordActivity.class);
//                                    intent.putExtra("patientInfo", patient);
//                                    startActivity(intent);
//                                }
//                            } else if (patientReferenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                                Toast.makeText(PatientVerifyOTPActivity.this, "ERROR: " + patientReferenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ).addOnFailureListener(e -> {
//                    Toast.makeText(PatientVerifyOTPActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                });
//    }


    private void setEventDelOtp(EditText editText, EditText editTextNext) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    editText.setText("");
                    editTextNext.requestFocus();
                }

                return false;
            }
        });
    }
}