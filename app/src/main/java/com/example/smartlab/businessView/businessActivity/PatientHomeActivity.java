package com.example.smartlab.businessView.businessActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessView.businessFragment.PatientCalendarFragment;
import com.example.smartlab.businessView.businessFragment.PatientHomeFragment;
import com.example.smartlab.businessView.businessFragment.PatientMedicineFragment;
import com.example.smartlab.businessView.businessFragment.PatientProfileFragment;
import com.example.smartlab.businessView.businessFragment.PatientServiceFragment;
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
    Patient patientIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        try {
            InitVariable();
            setEventListener();
        } catch (Exception ex) {
            Log.e("ERROR", "PatientHomeActivity|" + ex.getMessage());
            Toast.makeText(PatientHomeActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void InitVariable() {
        bottomNav = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().executePendingTransactions();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, homeFragment).commit();

        patientIntent = (Patient) getIntent().getSerializableExtra("patientInfo");

        // Truyền dữ liệu bệnh nhân sang fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("patientInfo", patientIntent);
        homeFragment.setArguments(bundle);
    }

    void setEventListener() {
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, homeFragment).commit();
                    getSupportFragmentManager().executePendingTransactions();

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

//    private void insertData() {
//        Doctor doctor1 = new Doctor(null,
//                "-Nn3DKiTzOgEN2bzlQ4l",
//                "-Nn0Og7tdrmx3mBUT4Lo",
//                "Bùi Hải Đăng",
//                true);
//
//        Doctor doctor2 = new Doctor(null,
//                "-Nn3DKiu0qQ2BJjLF7qm",
//                "-Nn0Og7tdrmx3mBUT4Lp",
//                "Trần Văn Đạt",
//                true);
//        Doctor doctor3 = new Doctor(null,
//                "-Nn3DKitfDveiLZOJeh9",
//                "-Nn0Og7sicns1hdLuq_p",
//                "Đặng Minh Hải",
//                true);
//        Doctor doctor4 = new Doctor(null,
//                "-Nn3DKiseqRs2L1ommBW",
//                "-Nn0Og7tdrmx3mBUT4Lp",
//                "Tăng Tự Phú",
//                false);
//        Doctor doctor5 = new Doctor(null,
//                "-Nn3DKiu0qQ2BJjLF7ql",
//                "-Nn0Og7tdrmx3mBUT4Lq",
//                "Nguyễn Gia Linh",
//                false);
//        Doctor doctor6 = new Doctor(null,
//                "-Nn3DKiu0qQ2BJjLF7ql",
//                "-Nn0Og7tdrmx3mBUT4Lo",
//                "Đặng Tiến Sơn",
//                false);
//        Doctor doctor7 = new Doctor(null,
//                "-Nn3DKiu0qQ2BJjLF7ql",
//                "-Nn0Og7tdrmx3mBUT4Lo",
//                "Lê Văn Quyết",
//                true);
//        Doctor doctor8 = new Doctor(null,
//                "-Nn3DKiu0qQ2BJjLF7qm",
//                "-Nn0Og7tdrmx3mBUT4Lq",
//                "Hoàng Đức Thắng",
//                true);
//        Doctor doctor9 = new Doctor(null,
//                "-Nn3DKitfDveiLZOJehA",
//                "-Nn0Og7sicns1hdLuq_p",
//                "Nguyễn Trọng Ninh",
//                false);
//        Doctor doctor10 = new Doctor(null,
//                "-Nn3DKitfDveiLZOJehA",
//                "-Nn0Og7tdrmx3mBUT4Lq",
//                "Nguyễn Đăng Nam",
//                false);
//        DoctorService.getInstance().Insert(doctor1).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor2).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor3).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor4).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor5).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor6).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor7).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor8).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor9).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        DoctorService.getInstance().Insert(doctor10).thenAccept(referenceInfo -> {
//            if (referenceInfo.getStatus() == ReferenceStatusEnum.SUCCESS) {
//
//            } else if (referenceInfo.getStatus() == ReferenceStatusEnum.ERROR) {
//                Toast.makeText(PatientHomeActivity.this, "ERROR: " + referenceInfo.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}