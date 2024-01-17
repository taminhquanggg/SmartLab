package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Medicine;
import com.example.smartlab.businessService.MedicineService;

public class MainStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        Medicine medicine = new Medicine();
        medicine.setDescription("Thuốc ngon bổ rẻ");
        medicine.setName ("Becberin");
        medicine.setUnit("Vien");
        medicine.setPrice(2000);
        medicine.setWeight("20");

        Medicine medicine1= new Medicine();
        medicine1.setDescription("Thuốc ngon bổ rẻ");
        medicine1.setName ("Becberin");
        medicine1.setUnit("Vien");
        medicine1.setPrice(2000);
        medicine1.setWeight("20");

        Medicine medicine2 = new Medicine();
        medicine2.setDescription("Thuốc ngon bổ rẻ");
        medicine2.setName ("Becberin");
        medicine2.setUnit("Vien");
        medicine2.setPrice(2000);
        medicine2.setWeight("20");

        Medicine medicine3 = new Medicine();
        medicine3.setDescription("Thuốc ngon bổ rẻ");
        medicine3.setName ("Becberin");
        medicine3.setUnit("Vien");
        medicine3.setPrice(2000);
        medicine3.setWeight("20");

        Medicine medicine4 = new Medicine();
        medicine4.setDescription("Thuốc ngon bổ rẻ");
        medicine4.setName ("Becberin");
        medicine4.setUnit("Vien");
        medicine4.setPrice(2000);
        medicine4.setWeight("20");
        MedicineService.getInstance().insert(medicine);

        MedicineService.getInstance().insert(medicine1);

        MedicineService.getInstance().insert(medicine2);

        MedicineService.getInstance().insert(medicine3);

        MedicineService.getInstance().insert(medicine4);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainStartActivity.this, MainGetStartedActivity.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        }, 3000);
    }
}