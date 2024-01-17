package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Patient;

public class PatientCongratulationsActivity extends AppCompatActivity {

    Patient patient;
    Button buttonStartBeginner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_congratulations);


        try {
            InitVariable();
            setEventListener();
        } catch (Exception ex) {
            Log.e("ERROR", "PatientCongratulationsActivity|" + ex.getMessage());
            Toast.makeText(PatientCongratulationsActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void InitVariable() {
        patient = (Patient) getIntent().getSerializableExtra("patientInfo");
        buttonStartBeginner = findViewById(R.id.button_startBeginner);
    }

    void setEventListener() {
        buttonStartBeginner.setOnClickListener(event -> {
            Intent intent = new Intent(getApplicationContext(), PatientHomeActivity.class);
            intent.putExtra("patientInfo", patient);
            startActivity(intent);
        });
    }
}