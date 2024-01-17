package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.HospitalMapAdapter;
import com.example.smartlab.businessAdapter.ScheduleHospitalAdapter;
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessService.BookingHospitalService;
import com.example.smartlab.businessService.HospitalService;
import com.example.smartlab.databinding.ActivityPatientScheduleHospitalBinding;

public class PatientScheduleHospitalActivity extends AppCompatActivity {

    Patient patientIntent;
    ActivityPatientScheduleHospitalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_schedule_hospital);

        try {
            InitVariable();
            setEventListener();
        } catch (Exception e) {
            Log.e("ERROR", "PatientScheduleHospitalActivity|" + e.getMessage());
            Toast.makeText(PatientScheduleHospitalActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        patientIntent = (Patient) getIntent().getSerializableExtra("patientInfo");

        binding = ActivityPatientScheduleHospitalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bindingScheduleHospital();

        //ShowHospitalLocation();
    }

    private void setEventListener() {
        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void bindingScheduleHospital() {
        BookingHospitalService.getInstance().getPatientBookingHospitalList(patientIntent.getPatientID())
                .addOnSuccessListener(bookingHospitals -> {

                    if (bookingHospitals.size() > 0) {

                        binding.rvListSchedule.setVisibility(View.VISIBLE);
                        binding.txtNone.setVisibility(View.INVISIBLE);

                        ScheduleHospitalAdapter scheduleHospitalAdapter = new ScheduleHospitalAdapter(this, bookingHospitals);
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

    public void handleViewScheduleDetail(BookingHospital bookingInfo) {
        Intent intent = new Intent(this, PatientScheduleHospitalDetailActivity.class);
        intent.putExtra("hospitalID", bookingInfo.getHospitalID());
        intent.putExtra("bookingInfo", bookingInfo);

        startActivity(intent);
    }
}