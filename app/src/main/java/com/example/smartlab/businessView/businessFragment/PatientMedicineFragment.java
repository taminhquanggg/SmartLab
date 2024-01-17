package com.example.smartlab.businessView.businessFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.MedicineAdapter;
import com.example.smartlab.businessService.MedicineService;
import com.example.smartlab.databinding.FragmentPatientMedicineBinding;

public class PatientMedicineFragment extends Fragment {

    RecyclerView recyclerViewSanpham ;
    RecyclerView recyclerViewSale ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patient_medicine, container, false);

        try {
            recyclerViewSanpham = FragmentPatientMedicineBinding.bind(view).recyclerViewSanpham;
            recyclerViewSanpham.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewSale = FragmentPatientMedicineBinding.bind(view).recycleViewSale;
            recyclerViewSale.setLayoutManager(new LinearLayoutManager(view.getContext() , LinearLayoutManager.HORIZONTAL, false));
            MedicineService.getInstance().getMedicineList().addOnSuccessListener(medicines -> {
                if (medicines.size() > 0) {

                    MedicineAdapter medicineAdapter = new MedicineAdapter(this, medicines);
                    recyclerViewSanpham.setAdapter(medicineAdapter);
                    MedicineAdapter medicineAdapter1 = new MedicineAdapter(this, medicines );
                    recyclerViewSale.setAdapter(medicineAdapter1);

                }
            }).addOnFailureListener(e -> {
                Log.e("ERROR", "PatientMedicineFragment| " + e.getMessage());

                Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            });
        }
        catch (Exception e){
            Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return view;
    }
}