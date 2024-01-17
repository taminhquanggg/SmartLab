package com.example.smartlab.businessView.businessFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessView.businessActivity.PatientHospitalMapsActivity;
import com.example.smartlab.businessView.businessActivity.PatientScheduleHospitalActivity;
import com.example.smartlab.databinding.FragmentPatientScheduleBinding;

public class PatientScheduleFragment extends Fragment {

    Button buttonScheduleHospital, buttonScheduleDoctor;

    Patient patientInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patient_schedule, container, false);

        try {
            InitVariable(view);
            setEventListener();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void InitVariable(View view) {
        buttonScheduleHospital = FragmentPatientScheduleBinding.bind(view).buttonScheduleHospital;
        buttonScheduleDoctor = FragmentPatientScheduleBinding.bind(view).buttonScheduleDoctor;

        patientInfo = (Patient) getArguments().getSerializable("patientInfo");
    }

    private void setEventListener() {
        buttonScheduleHospital.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), PatientScheduleHospitalActivity.class);
            intent.putExtra("patientInfo", patientInfo);
            startActivity(intent);
        });
    }
}