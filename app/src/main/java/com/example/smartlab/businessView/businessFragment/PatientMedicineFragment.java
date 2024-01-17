package com.example.smartlab.businessView.businessFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.car.ui.recyclerview.CarUiRadioButtonListItem;
import com.example.smartlab.R;
import com.example.smartlab.businessAdapter.MedicineAdapter;
import com.example.smartlab.businessObject.Medicine;
import com.example.smartlab.businessObject.MedicineCard;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessService.MedicineCardService;
import com.example.smartlab.businessService.MedicineService;
import com.example.smartlab.businessView.businessActivity.PatientMedicineCartActivity;
import com.example.smartlab.businessView.businessActivity.PatientMedicineDetailActivity;
import com.example.smartlab.databinding.FragmentPatientMedicineBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PatientMedicineFragment extends Fragment {

    RecyclerView recyclerViewSanpham;

    Patient patientInfo;

    FloatingActionButton button_Card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patient_medicine, container, false);

        try {
            patientInfo = (Patient) getArguments().getSerializable("patientInfo");

            recyclerViewSanpham = FragmentPatientMedicineBinding.bind(view).recyclerViewSanpham;
            recyclerViewSanpham.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

            MedicineService.getInstance().getMedicineList().addOnSuccessListener(medicines -> {
                if (medicines.size() > 0) {
                    MedicineAdapter medicineAdapter = new MedicineAdapter(this, medicines);
                    recyclerViewSanpham.setAdapter(medicineAdapter);
                }
            }).addOnFailureListener(e -> {
                Log.e("ERROR", "PatientMedicineFragment| " + e.getMessage());
                Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            });

            button_Card = FragmentPatientMedicineBinding.bind(view).buttonCard;

            button_Card.setOnClickListener(v -> {
                Intent intent = new Intent(this.getContext(), PatientMedicineCartActivity.class);
                intent.putExtra("patientInfo", patientInfo);
                startActivity(intent);
            });
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return view;
    }

    public void handlerViewMedicineDetail(Medicine medicineInfo) {
        Intent intent = new Intent(this.getContext(), PatientMedicineDetailActivity.class);
        intent.putExtra("patientInfo", patientInfo);
        intent.putExtra("medicineInfo", medicineInfo);

        startActivity(intent);
    }

    public void insertToCardMedicine(Medicine medicineInfo) {
        MedicineCard medicineCard = new MedicineCard(patientInfo.getPatientID(),
                medicineInfo.getMedicineID(),
                medicineInfo.getName(),
                medicineInfo.getPrice());

        MedicineCardService.getInstance().Insert(medicineCard).addOnSuccessListener(new OnSuccessListener<ReferenceInfo<MedicineCard>>() {
            @Override
            public void onSuccess(ReferenceInfo<MedicineCard> medicineCardReferenceInfo) {

            }
        });
    }
}