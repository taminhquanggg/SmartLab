package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.BookingDoctor;
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.Doctor;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.example.smartlab.businessService.BookingDoctorService;
import com.example.smartlab.businessService.BookingHospitalService;
import com.example.smartlab.databinding.ActivityPatientViewDoctorDetailBinding;

import java.util.Calendar;

public class PatientViewDoctorDetailActivity extends AppCompatActivity {

    ActivityPatientViewDoctorDetailBinding binding;

    Patient patientIntent;
    Doctor doctorIntent;
    Calendar calendarBook = Calendar.getInstance();

    String hourBook, dateBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_doctor_detail);

        patientIntent = (Patient) getIntent().getSerializableExtra("patientInfo");
        doctorIntent = (Doctor) getIntent().getSerializableExtra("doctorInfo");


        binding = ActivityPatientViewDoctorDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtDoctorName.setText(doctorIntent.getDoctorName());
        binding.txtDepartment.setText(doctorIntent.getDepartmentName());
        binding.txtHospital.setText(doctorIntent.getHospitalName());

        binding.buttonChooseHour.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            calendarBook.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendarBook.set(Calendar.MINUTE, minute);

                            hourBook = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);

                            binding.txtChooseHour.setText(hourBook);
                        }
                    }, calendarBook.get(Calendar.HOUR_OF_DAY),
                    calendarBook.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        });

        binding.buttonChooseDay.setOnClickListener(v -> {
            int year = calendarBook.get(Calendar.YEAR);
            int month = calendarBook.get(Calendar.MONTH);
            int day = calendarBook.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    PatientViewDoctorDetailActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        calendarBook.set(Calendar.YEAR, selectedYear);
                        calendarBook.set(Calendar.MONTH, selectedMonth);
                        calendarBook.set(Calendar.DAY_OF_MONTH, selectedDay);

                        dateBook = Integer.toString(selectedDay) + "/" +
                                Integer.toString((selectedMonth + 1)) + "/" +
                                Integer.toString(selectedYear);


                        binding.txtChooseDay.setText(dateBook);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        binding.buttonBook.setOnClickListener(v -> {
            BookingDoctor bookingDoctor = new BookingDoctor();
            bookingDoctor.setDoctorID(doctorIntent.getDoctorID());
            bookingDoctor.setPatientID(patientIntent.getPatientID());
            bookingDoctor.setDateBooking(dateBook);
            bookingDoctor.setHourBooking(hourBook);

            BookingDoctorService.getInstance().Insert(bookingDoctor)
                    .addOnSuccessListener(bookingHospitalReferenceInfo -> {
                        if (bookingHospitalReferenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
                            Toast.makeText(PatientViewDoctorDetailActivity.this, "Đặt hẹn với bác sĩ thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (bookingHospitalReferenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
                            Toast.makeText(PatientViewDoctorDetailActivity.this, "Đặt hẹn với bác sĩ không thành công", Toast.LENGTH_SHORT).show();
                        }

                    }).addOnFailureListener(e -> {
                        Log.e("ERROR", "PatientViewDoctorDetailActivity|" + e.getMessage());
                        Toast.makeText(PatientViewDoctorDetailActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });
    }
}