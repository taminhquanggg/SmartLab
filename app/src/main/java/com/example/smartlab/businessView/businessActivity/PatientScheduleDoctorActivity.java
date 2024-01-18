package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.BookingDoctorAdapter;
import com.example.smartlab.businessAdapter.ScheduleHospitalAdapter;
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessService.BookingDoctorService;
import com.example.smartlab.businessService.BookingHospitalService;
import com.example.smartlab.databinding.ActivityPatientScheduleDoctorBinding;
import com.example.smartlab.databinding.ActivityPatientScheduleHospitalBinding;

public class PatientScheduleDoctorActivity extends AppCompatActivity {

    Patient patientIntent;
    ActivityPatientScheduleDoctorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_schedule_doctor);

        try {
            InitVariable();
            setEventListener();
        } catch (Exception e) {
            Log.e("ERROR", "PatientScheduleDoctorActivity|" + e.getMessage());
            Toast.makeText(PatientScheduleDoctorActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        patientIntent = (Patient) getIntent().getSerializableExtra("patientInfo");

        binding = ActivityPatientScheduleDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bindingScheduleDoctor();
    }

    private void setEventListener() {
        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void bindingScheduleDoctor() {
        BookingDoctorService.getInstance().getPatientBookingDoctorList(patientIntent.getPatientID())
                .addOnSuccessListener(bookingDoctors -> {
                    if (bookingDoctors.size() > 0) {

                        binding.rvListSchedule.setVisibility(View.VISIBLE);
                        binding.txtNone.setVisibility(View.INVISIBLE);

                        BookingDoctorAdapter scheduleHospitalAdapter = new BookingDoctorAdapter(this, bookingDoctors);
                        binding.rvListSchedule.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

                        binding.rvListSchedule.setAdapter(scheduleHospitalAdapter);
                    }
                    else {
                        binding.rvListSchedule.setVisibility(View.INVISIBLE);
                        binding.txtNone.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(e -> {
                    Log.e("ERROR", "PatientScheduleHospitalActivity|" + e.getMessage());
                    Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}