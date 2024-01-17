package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.HospitalMapAdapter;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessService.HospitalService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PatientBookDoctorActivity extends AppCompatActivity {

    @Overridev
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_doctor);
    }
    public void ShowDoctorBook() {
        HospitalService.getInstance().getHospitalList().addOnSuccessListener(hospitalArrayList -> {
            if (hospitalArrayList.size() > 0) {
                for (Hospital hospital : hospitalArrayList) {
                    LatLng hospitalLatLng = new LatLng(hospital.getLatitude(), hospital.getLongitude());
                    Marker makerHospital = myMap.addMarker(new MarkerOptions()
                            .position(hospitalLatLng)
                            .title(hospital.getHospitalName()));
                    if (makerHospital != null) {
                        makerHospital.setTag(hospital);
                    }
                }

                HospitalMapAdapter hospitalMapAdapter = new HospitalMapAdapter(this, hospitalArrayList);
                binding.rvListHospitalMap.setAdapter(hospitalMapAdapter);

                this.hospitalArrayList = hospitalArrayList;
            }
        }).addOnFailureListener(e -> {
            Log.e("ERROR", "ShowHospitalLocation|" + e.getMessage());
            Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}