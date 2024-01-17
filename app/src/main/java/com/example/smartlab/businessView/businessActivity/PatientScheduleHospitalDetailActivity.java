package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessService.HospitalService;
import com.example.smartlab.databinding.ActivityPatientScheduleHospitalBinding;
import com.example.smartlab.databinding.ActivityPatientScheduleHospitalDetailBinding;

public class PatientScheduleHospitalDetailActivity extends AppCompatActivity {
    BookingHospital bookingIntent;

    ActivityPatientScheduleHospitalDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_schedule_hospital_detail);

        try {
            InitVariable();
            setEventListener();
        } catch (Exception e) {
            Log.e("ERROR", "PatientScheduleHospitalDetailActivity|" + e.getMessage());
            Toast.makeText(PatientScheduleHospitalDetailActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        bookingIntent = (BookingHospital) getIntent().getSerializableExtra("bookingInfo");
        binding = ActivityPatientScheduleHospitalDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HospitalService.getInstance().getHospitalInfo(bookingIntent.getHospitalID())
                .addOnSuccessListener(hospital -> {
                    if (hospital != null) {
                        binding.txtHospitalName.setText(hospital.getHospitalName());
                        binding.txtHospitalAddress.setText(hospital.getAddress());
                        binding.txtReason.setText(bookingIntent.getReason());
                        binding.txtChooseDay.setText(bookingIntent.getDateBooking());
                        binding.txtChooseHour.setText(bookingIntent.getHourBooking());
                    }
                }).addOnFailureListener(e -> {
                    Log.e("abc", e.getMessage());
                });
    }

    private void setEventListener() {

        binding.buttonDirectMap.setOnClickListener(v -> {
            HospitalService.getInstance().getHospitalInfo(bookingIntent.getHospitalID())
                    .addOnSuccessListener(hospital -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("google.navigation:q=" + hospital.getLatitude() + "," + hospital.getLongitude()));
                        startActivity(intent);
                    }).addOnFailureListener(e -> {
                        Log.e("abc", e.getMessage());
                    });
        });

        binding.buttonCallHotline.setOnClickListener(v -> {
            HospitalService.getInstance().getHospitalInfo(bookingIntent.getHospitalID())
                    .addOnSuccessListener(hospital -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + hospital.getHotline()));
                        startActivity(intent);
                    }).addOnFailureListener(e -> {
                        Log.e("abc", e.getMessage());
                    });
        });

        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });
    }
}