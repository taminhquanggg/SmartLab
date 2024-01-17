package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.MonthYearPickerDialog;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.databinding.ActivityPatientBookHospitalBinding;

import java.util.Calendar;


public class PatientBookHospitalActivity extends AppCompatActivity {

    Patient patientIntent;
    Hospital hospitalIntent;

    private ActivityPatientBookHospitalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_hospital);

        try {
            binding = ActivityPatientBookHospitalBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            InitVariable();
            setEventListener();
        } catch (Exception ex) {
            Log.e("ERROR", "PatientBookHospitalActivity|" + ex.getMessage());
            Toast.makeText(PatientBookHospitalActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        patientIntent = (Patient) getIntent().getSerializableExtra("patientInfo");
        hospitalIntent = (Hospital) getIntent().getSerializableExtra("hospitalInfo");

        if (hospitalIntent != null) {
            binding.txtHospitalName.setText(hospitalIntent.getHospitalName());
            binding.txtHospitalAddress.setText(hospitalIntent.getAddress());
        }
    }

    private void setEventListener() {
        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });

        binding.buttonChooseMonth.setOnClickListener(v -> {
            MonthYearPickerDialog dialog = new MonthYearPickerDialog();
            dialog.setListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    int m = month + 1;
                    String text = "Tháng " + (m < 10 ? "0" : "") + Integer.toString(m) + ", " + Integer.toString(year);
                    binding.buttonChooseMonth.setText(text);
                }
            });

            dialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
        });

        binding.buttonToday.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int m = cal.get(Calendar.MONTH) + 1;
            int y = cal.get(Calendar.YEAR);
            String text = "Tháng " + (m < 10 ? "0" : "") + Integer.toString(m) + ", " + Integer.toString(y);
            binding.buttonChooseMonth.setText(text);
        });
    }
}