package com.example.smartlab.businessView.businessActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.HospitalMapAdapter;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessService.HospitalService;
import com.example.smartlab.databinding.ActivityPatientHospitalMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientHospitalMapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap myMap;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ActivityPatientHospitalMapsBinding binding;
    private Hospital hospitalChoose;
    private ArrayList<Hospital> hospitalArrayList;
    private Patient patientIntent;

    public Patient getPatientIntent() {
        return patientIntent;
    }

    public void setVisibleHospitalMap(int status) {
        binding.rvListHospitalMap.setVisibility(status);
    }

    public void setVisibleInfoHospital(int status) {
        binding.layoutInfoHospital.setVisibility(status);
    }

    public void setHospitalInfo(Hospital hospital) {
        this.hospitalChoose = hospital;
        binding.txtHospitalNameInfo.setText(hospital.getHospitalName());
        binding.txtHospitalAddressInfo.setText(hospital.getAddress());
        binding.txtHospitalHotlineInfo.setText(hospital.getHotline());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            InitVariable();
            setEventListener();
        } catch (Exception e) {
            Log.e("ERROR", "PatientHospitalMapsActivity|" + e.getMessage());
            Toast.makeText(PatientHospitalMapsActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        MoveToLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), "Vị trí của bạn");
        ShowHospitalLocation();
        myMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission is denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        LatLng position = marker.getPosition();

        myMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(position, 18f)
        );

        binding.rvListHospitalMap.setVisibility(View.INVISIBLE);
        binding.layoutInfoHospital.setVisibility(View.VISIBLE);

        Hospital hospitalMaker = (Hospital) marker.getTag();

        if (hospitalMaker != null) {
            binding.txtHospitalNameInfo.setText(hospitalMaker.getHospitalName());
            binding.txtHospitalAddressInfo.setText(hospitalMaker.getAddress());
            binding.txtHospitalHotlineInfo.setText(hospitalMaker.getHotline());

            BottomSheetBehavior behavior = BottomSheetBehavior.from(binding.bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            binding.rvListHospitalMap.setVisibility(View.VISIBLE);
            binding.layoutInfoHospital.setVisibility(View.INVISIBLE);
        }


        return false;
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(PatientHospitalMapsActivity.this);
                }
            }
        });
    }

    private void InitVariable() {

        patientIntent = (Patient) getIntent().getSerializableExtra("patientInfo");

        binding = ActivityPatientHospitalMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        BottomSheetBehavior behavior = BottomSheetBehavior.from(binding.bottomSheet);
        behavior.setPeekHeight(150);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        binding.rvListHospitalMap.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setEventListener() {
        binding.buttonGetCurrentLocation.setOnClickListener(v -> {
            binding.rvListHospitalMap.setVisibility(View.VISIBLE);
            binding.layoutInfoHospital.setVisibility(View.INVISIBLE);
            MoveToLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), "Vị trí của bạn");
        });

        binding.buttonBackListHospital.setOnClickListener(v -> {
            binding.rvListHospitalMap.setVisibility(View.VISIBLE);
            binding.layoutInfoHospital.setVisibility(View.INVISIBLE);
        });

        binding.buttonDirectionHospital.setOnClickListener(v -> {
            if (hospitalChoose != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + hospitalChoose.getLatitude() + "," + hospitalChoose.getLongitude()));
                startActivity(intent);
            }
        });

        binding.buttonContactHospital.setOnClickListener(v -> {
            if (hospitalChoose != null) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + hospitalChoose.getHotline()));
                startActivity(intent);
            }
        });

        binding.buttonBookHospital.setOnClickListener(v -> {
            handlerPatientBookHospital(hospitalChoose);
        });

        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });

        binding.seachViewSearchHospital.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (hospitalArrayList.size() > 0) {
//
//                    List<Hospital> filteredList = hospitalArrayList.stream()
//                            .filter(item -> item.getHospitalName().toLowerCase().contains(newText.toLowerCase()))
//                            .collect(Collectors.toList());
//
//                    binding.seachViewSearchHospital.setSuggestionsAdapter(new ArrayAdapter<>(, android.R.layout.simple_list_item_1));
//
//                }

                return false;
            }
        });
    }


    public void MoveToLocation(double latitude, double longitude, String title) {
        LatLng locationLatLng = new LatLng(latitude, longitude);

        myMap.addMarker(new MarkerOptions()
                .position(locationLatLng)
                .title(title));
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 18f), 1000, null);
        myMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void ShowHospitalLocation() {
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

    public void handlerPatientBookHospital(Hospital hospital) {
        if (patientIntent != null && hospital != null) {
            Intent intent = new Intent(getApplicationContext(), PatientBookHospitalActivity.class);
            intent.putExtra("patientInfo", patientIntent);
            intent.putExtra("hospitalInfo", hospital);
            startActivity(intent);
        }
    }
}