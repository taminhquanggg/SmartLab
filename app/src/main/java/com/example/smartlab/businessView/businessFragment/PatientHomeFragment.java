package com.example.smartlab.businessView.businessFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.DoctorHomeAdapter;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessService.DoctorService;
import com.example.smartlab.businessView.businessActivity.PatientCongratulationsActivity;
import com.example.smartlab.businessView.businessActivity.PatientHospitalMapsActivity;
import com.example.smartlab.databinding.FragmentPatientHomeBinding;

public class PatientHomeFragment extends Fragment {
    TextView txtHome_PatientName;
    Patient patientInfo;
    RecyclerView recyclerViewDoctor;
    Button buttonBookHospitalHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_home, container, false);

        try {
            InitVariable(view);
            setEventListener();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void InitVariable(View view) {

        txtHome_PatientName = FragmentPatientHomeBinding.bind(view).txtPatientName;

        buttonBookHospitalHome = FragmentPatientHomeBinding.bind(view).buttonBookHospitalHome;

        patientInfo = (Patient) getArguments().getSerializable("patientInfo");

        if (txtHome_PatientName != null) {
            txtHome_PatientName.setText(patientInfo.getPatientName());
        }

        recyclerViewDoctor = FragmentPatientHomeBinding.bind(view).rvListDoctorPatientHome;
        recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        DoctorService.getInstance().getDoctorList().addOnSuccessListener(doctorArrayList -> {
            if (doctorArrayList.size() > 0) {
                doctorArrayList.removeIf(doctorItem -> !doctorItem.isTrending());

                DoctorHomeAdapter doctorHomeAdapter = new DoctorHomeAdapter(this, doctorArrayList);
                recyclerViewDoctor.setAdapter(doctorHomeAdapter);
            }
        }).addOnFailureListener(e -> {
            Log.e("ERROR", "FragmentPatientHomeBinding| " + e.getMessage());

            Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void setEventListener() {
        buttonBookHospitalHome.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), PatientHospitalMapsActivity.class);
            intent.putExtra("patientInfo", patientInfo);
            startActivity(intent);
        });
    }
}