package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.DoctorAdapter;
import com.example.smartlab.businessAdapter.HospitalMapAdapter;
import com.example.smartlab.businessObject.Doctor;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessService.DoctorService;
import com.example.smartlab.businessService.HospitalService;
import com.example.smartlab.databinding.ActivityPatientBookDoctorBinding;
import com.example.smartlab.databinding.ActivityPatientHospitalMapsBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PatientBookDoctorActivity extends AppCompatActivity {

    Patient patientIntent;


    ActivityPatientBookDoctorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_doctor);

        patientIntent = (Patient) getIntent().getSerializableExtra("patientInfo");

        binding = ActivityPatientBookDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvListDoctor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        DoctorService.getInstance().getDoctorList().addOnSuccessListener(doctorArrayList -> {
            if (doctorArrayList.size() > 0) {
                DoctorAdapter doctorAdapter = new DoctorAdapter(this, doctorArrayList);
                binding.rvListDoctor.setAdapter(doctorAdapter);
            }
        });


        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void handlerViewDetailDoctor(Doctor doctor) {
        Intent intent = new Intent(this, PatientViewDoctorDetailActivity.class);
        intent.putExtra("patientInfo", patientIntent);
        intent.putExtra("doctorInfo", doctor);

        startActivity(intent);
    }
}