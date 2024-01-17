package com.example.smartlab.businessView.businessActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.databinding.ActivityPatientBookingSuccessBinding;
import com.google.gson.Gson;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PatientBookingSuccessActivity extends AppCompatActivity {

    BookingHospital bookingIntent;
    private ActivityPatientBookingSuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_booking_success);

        try {
            binding = ActivityPatientBookingSuccessBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            InitVariable();
            setEventListener();
        } catch (Exception ex) {
            Log.e("ERROR", "PatientBookHospitalActivity|" + ex.getMessage());
            Toast.makeText(PatientBookingSuccessActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        bookingIntent = (BookingHospital) getIntent().getSerializableExtra("bookingInfo");

        if (bookingIntent != null) {
            Gson gson = new Gson();
            String jsonDoctor = gson.toJson(bookingIntent);

            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

            Display display = manager.getDefaultDisplay();

            Point point = new Point();
            display.getSize(point);

            int width = point.x;
            int height = point.y;
            int dimen = Math.min(width, height);
            dimen = dimen * 3 / 4;
            QRGEncoder qrgEncoder = new QRGEncoder(jsonDoctor, null, QRGContents.Type.TEXT, dimen);
            Bitmap bitmap = qrgEncoder.getBitmap();
            binding.picQRCode.setImageBitmap(bitmap);
        }
    }

    private void setEventListener() {
        binding.buttonDone.setOnClickListener(v -> {
            finish();
        });
    }
}