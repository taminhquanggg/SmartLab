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
import com.example.smartlab.businessService.MedicineCardService;
import com.example.smartlab.businessService.MedicineService;
import com.example.smartlab.databinding.ActivityPatientMedicineCartBinding;
import com.example.smartlab.databinding.FragmentPatientMedicineBinding;

public class PatientMedicineCartActivity extends AppCompatActivity {

    RecyclerView recyclerViewCard;

    private ActivityPatientMedicineCartBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medicine_cart);
        binding = ActivityPatientMedicineCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MedicineCardService.getInstance().getMedicineCardList().addOnSuccessListener(medicines1 -> {
            if (medicines1.size() > 0) {

                MedicineCardAdapter medicineCardAdapter = new MedicineCardAdapter(this, medicines1);
                recyclerViewCard.setAdapter(medicineCardAdapter);
            }
        }).addOnFailureListener(e -> {
            Log.e("ERROR", "PatientMedicineFragment| " + e.getMessage());

            Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();

        });
    }
}

//    @Override
//    public View fghdfgdf(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.activity_patient_medicine_cart, container, false);
//        try {
//            recyclerViewCard = ActivityPatientMedicineCartBinding.bind(view).recyclerViewCart;
//            recyclerViewCard.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
//            MedicineCardService.getInstance().getMedicineCardList().addOnSuccessListener(medicines1 -> {
//                if (medicines1.size() > 0) {
//
//                    MedicineCardAdapter medicineCardAdapter = new MedicineCardAdapter(this, medicines1);
//                    recyclerViewCard.setAdapter(medicineCardAdapter);
//                }
//            }).addOnFailureListener(e -> {
//                Log.e("ERROR", "PatientMedicineFragment| " + e.getMessage());
//
//                Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//            });
//        } catch (Exception e) {
//            Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//        }
//        return view;
//    }

