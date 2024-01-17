package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Doctor;
import com.example.smartlab.businessService.DoctorService;

public class MainStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        Doctor Doctor1 = new Doctor();
        Doctor1.setDoctorName("Đặng Minh Hải");
        Doctor1.setHospitalName("Bệnh viện đa khoa Vĩnh Tường");
        Doctor1.setDepartmentName("Khoa răng hàm mặt");
        Doctor1.setTrending(true);

        Doctor Doctor2 = new Doctor();
        Doctor2.setDoctorName("Trần Khánh Linh");
        Doctor2.setHospitalName("Bệnh viện đa khoa Thạch Thất");
        Doctor2.setDepartmentName("Khoa răng hàm mặt");
        Doctor2.setTrending(true);

        Doctor Doctor3 = new Doctor();
        Doctor3.setDoctorName("Tăng Tự Phú");
        Doctor3.setHospitalName("Bệnh viện Bạch Mai");
        Doctor3.setDepartmentName("Khoa răng hàm mặt");
        Doctor3.setTrending(true);

        Doctor Doctor4 = new Doctor();
        Doctor4.setDoctorName("Nguyễn Trọng Ninh");
        Doctor4.setHospitalName("Bệnh viện Bạch Mai");
        Doctor4.setDepartmentName("Khoa răng hàm mặt");
        Doctor4.setTrending(true);

        Doctor Doctor5 = new Doctor();
        Doctor5.setDoctorName("Đặng Tiến Sơn");
        Doctor5.setHospitalName("Bệnh viện Bạch Mai");
        Doctor5.setDepartmentName("Khoa răng hàm mặt");
        Doctor5.setTrending(true);

//        DoctorService.getInstance().Insert(Doctor1);
//        DoctorService.getInstance().Insert(Doctor2);
//        DoctorService.getInstance().Insert(Doctor3);
//        DoctorService.getInstance().Insert(Doctor4);
//        DoctorService.getInstance().Insert(Doctor5);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainStartActivity.this, PatientGetOTPActivity.class));
                finish();
            }
        }, 3000);
    }
}
