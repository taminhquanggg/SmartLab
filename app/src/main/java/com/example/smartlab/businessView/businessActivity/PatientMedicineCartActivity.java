package com.example.smartlab.businessView.businessActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.MedicineAdapter;
import com.example.smartlab.businessAdapter.MedicineCardAdapter;
import com.example.smartlab.businessObject.MedicineCard;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessService.MedicineCardService;
import com.example.smartlab.businessService.MedicineService;
import com.example.smartlab.databinding.ActivityPatientMedicineCartBinding;
import com.example.smartlab.databinding.FragmentPatientMedicineBinding;

public class PatientMedicineCartActivity extends AppCompatActivity {

    private ActivityPatientMedicineCartBinding binding;

    Patient patientInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medicine_cart);

        patientInfo = (Patient) getIntent().getSerializableExtra("patientInfo");

        binding = ActivityPatientMedicineCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        bindingListCard();
    }

    public void bindingListCard() {
        MedicineCardService.getInstance().getMedicineCardList(patientInfo.getPatientID())
                .addOnSuccessListener(medicines -> {
                    if (medicines.size() > 0) {
                        binding.txtNone.setVisibility(View.INVISIBLE);
                        binding.layoutCard.setVisibility(View.VISIBLE);

                        MedicineCardAdapter medicineCardAdapter = new MedicineCardAdapter(this, medicines);
                        binding.recyclerViewCart.setAdapter(medicineCardAdapter);

                        int Tong = 0;

                        for (MedicineCard medicineCard : medicines) {
                            Tong += medicineCard.getPrice();
                        }

                        binding.txtTongTien.setText(Integer.toString(Tong));
                    } else {
                        binding.txtNone.setVisibility(View.VISIBLE);
                        binding.layoutCard.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(e -> {
                    Log.e("ERROR", "PatientMedicineFragment| " + e.getMessage());

                    Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                });
    }
}