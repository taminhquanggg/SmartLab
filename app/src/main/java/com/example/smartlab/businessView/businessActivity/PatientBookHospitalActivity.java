package com.example.smartlab.businessView.businessActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.example.smartlab.businessService.BookingHospitalService;
import com.example.smartlab.databinding.ActivityPatientBookHospitalBinding;
import com.google.gson.Gson;
import com.google.zxing.WriterException;


import java.util.Calendar;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class PatientBookHospitalActivity extends AppCompatActivity {

    Patient patientIntent;
    Hospital hospitalIntent;
    private ActivityPatientBookHospitalBinding binding;
    Calendar calendarBook = Calendar.getInstance();

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

        binding.buttonChooseDay.setText(generateTextDateBookHospital(calendarBook));
        binding.buttonChooseHour.setText(generateTextHourBookHospital(calendarBook));
    }

    private void setEventListener() {
        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });

        binding.buttonChooseDay.setOnClickListener(v -> {

            int year = calendarBook.get(Calendar.YEAR);
            int month = calendarBook.get(Calendar.MONTH);
            int day = calendarBook.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    PatientBookHospitalActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        calendarBook.set(Calendar.YEAR, selectedYear);
                        calendarBook.set(Calendar.MONTH, selectedMonth);
                        calendarBook.set(Calendar.DAY_OF_MONTH, selectedDay);

                        binding.buttonChooseDay.setText(generateTextDateBookHospital(calendarBook));
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        binding.buttonChooseHour.setOnClickListener(v -> {

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            calendarBook.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendarBook.set(Calendar.MINUTE, minute);

                            binding.buttonChooseHour.setText(generateTextHourBookHospital(calendarBook));
                        }
                    }, calendarBook.get(Calendar.HOUR_OF_DAY),
                    calendarBook.get(Calendar.MINUTE), true);
            timePickerDialog.show();

        });



        binding.buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingHospital bookingHospital = new BookingHospital();
                bookingHospital.setHospitalID(hospitalIntent.getHospitalID());
                bookingHospital.setPatientID(patientIntent.getPatientID());
                bookingHospital.setReason(binding.inputReason.getText().toString());
                bookingHospital.setDateBooking(generateDateBookingHospital(calendarBook));
                bookingHospital.setHourBooking(calendarBook.get(Calendar.HOUR_OF_DAY) + ":" + calendarBook.get(Calendar.MINUTE));

                BookingHospitalService.getInstance().Insert(bookingHospital)
                        .addOnSuccessListener(bookingHospitalReferenceInfo -> {
                            if (bookingHospitalReferenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
                                Intent intent = new Intent(getApplicationContext(), PatientBookingSuccessActivity.class);
                                intent.putExtra("bookingInfo", bookingHospitalReferenceInfo.getData());
                                startActivity(intent);
                                finish();
                            } else if (bookingHospitalReferenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
                                Log.e("ERROR", "PatientBookHospitalActivity|" + bookingHospitalReferenceInfo.getMessage());
                                Toast.makeText(PatientBookHospitalActivity.this, "ERROR: " + bookingHospitalReferenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }).addOnFailureListener(e -> {
                            Log.e("ERROR", "PatientBookHospitalActivity|" + e.getMessage());
                            Toast.makeText(PatientBookHospitalActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }


    private String generateTextDateBookHospital(Calendar calendarBook) {

        int day = calendarBook.get(Calendar.DAY_OF_MONTH);
        int month = calendarBook.get(Calendar.MONTH) + 1;
        int year = calendarBook.get(Calendar.YEAR);

        String dayText = (day < 10) ? "0" + Integer.toString(day) : Integer.toString(day);
        String monthText = (month < 10) ? "0" + Integer.toString(month) : Integer.toString(month);

        String dayOfWeek = calendarBook.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("vi", "VN"));

        return dayOfWeek + ", " + dayText + "/" + monthText + "/" + Integer.toString(year);
    }

    private String generateTextHourBookHospital(Calendar calendarBook) {

        int hour = calendarBook.get(Calendar.HOUR_OF_DAY);
        int minute = calendarBook.get(Calendar.MINUTE);

        String hourText = (hour < 10) ? "0" + Integer.toString(hour) : Integer.toString(hour);
        String minuteText = (minute < 10) ? "0" + Integer.toString(minute) : Integer.toString(minute);

        return hourText + ":" + minuteText;
    }

    private String generateDateBookingHospital(Calendar calendarBook) {

        int day = calendarBook.get(Calendar.DAY_OF_MONTH);
        int month = calendarBook.get(Calendar.MONTH) + 1;
        int year = calendarBook.get(Calendar.YEAR);

        return day + "/" + month + "/" + year;
    }
}