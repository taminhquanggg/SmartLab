package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Medicine;
import com.example.smartlab.businessObject.MedicineCard;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessService.MedicineCardService;
import com.example.smartlab.databinding.ActivityPatientBookHospitalBinding;
import com.example.smartlab.databinding.ActivityPatientMedicineDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;

public class PatientMedicineDetailActivity extends AppCompatActivity {

    Patient patientInfo;
    Medicine medicineInfo;
    ActivityPatientMedicineDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medicine_detail);

        try {
            binding = ActivityPatientMedicineDetailBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            InitVariable();

            binding.buttonCard.setOnClickListener(v -> {
                Intent intent = new Intent(this, PatientMedicineCartActivity.class);
                intent.putExtra("patientInfo", patientInfo);
                startActivity(intent);
            });

            binding.buttonAddToCard.setOnClickListener(v -> {
                MedicineCard medicineCard = new MedicineCard(patientInfo.getPatientID(),
                        medicineInfo.getMedicineID(),
                        medicineInfo.getName(),
                        medicineInfo.getPrice());

                MedicineCardService.getInstance().Insert(medicineCard).addOnSuccessListener(new OnSuccessListener<ReferenceInfo<MedicineCard>>() {
                    @Override
                    public void onSuccess(ReferenceInfo<MedicineCard> medicineCardReferenceInfo) {
                        Toast.makeText(PatientMedicineDetailActivity.this, "Thêm thành công sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                    }
                });
            });
        } catch (Exception ex) {
            Log.e("ERROR", "PatientMedicineDetailActivity|" + ex.getMessage());
            Toast.makeText(PatientMedicineDetailActivity.this, "ERROR: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InitVariable() {
        patientInfo = (Patient) getIntent().getSerializableExtra("patientInfo");
        medicineInfo = (Medicine) getIntent().getSerializableExtra("medicineInfo");

        binding.TextViewName.setText(medicineInfo.getName());
        binding.textViewWeight.setText(medicineInfo.getWeight());
        binding.textViewUnit.setText(medicineInfo.getUnit());
        binding.txtPrice.setText(Integer.toString(medicineInfo.getPrice()));
        binding.textViewMieuTaThuoc.setText(medicineInfo.getDescription());
    }
}