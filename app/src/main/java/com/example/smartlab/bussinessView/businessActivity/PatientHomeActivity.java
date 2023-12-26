package com.example.smartlab.bussinessView.businessActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.smartlab.bussinessView.businessFragment.PatientCalendarFragment;
import com.example.smartlab.bussinessView.businessFragment.PatientHomeFragment;
import com.example.smartlab.bussinessView.businessFragment.PatientMedicineFragment;
import com.example.smartlab.bussinessView.businessFragment.PatientProfileFragment;
import com.example.smartlab.bussinessView.businessFragment.PatientServiceFragment;
import com.example.smartlab.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PatientHomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    PatientHomeFragment homeFragment = new PatientHomeFragment();
    PatientCalendarFragment calendarFragment = new PatientCalendarFragment();
    PatientServiceFragment serviceFragment = new PatientServiceFragment();
    PatientMedicineFragment medicineFragment = new PatientMedicineFragment();
    PatientProfileFragment profileFragment = new PatientProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        bottomNav = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, homeFragment).commit();
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, homeFragment).commit();
                    return true;
                } else if (id == R.id.bottom_nav_calendar) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, calendarFragment).commit();
                    return true;
                } else if (id == R.id.bottom_nav_service) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, serviceFragment).commit();
                    return true;
                } else if (id == R.id.bottom_nav_medicine) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, medicineFragment).commit();

                    return true;
                } else if (id == R.id.bottom_nav_profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, profileFragment).commit();
                    return true;
                }

                return false;
            }
        });
    }

}