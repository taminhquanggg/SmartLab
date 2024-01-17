package com.example.smartlab.businessAdapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Medicine;
import com.example.smartlab.businessView.businessActivity.PatientHomeActivity;
import com.example.smartlab.businessView.businessActivity.PatientMedicineDetailActivity;
import com.example.smartlab.businessView.businessFragment.PatientMedicineFragment;
import com.example.smartlab.databinding.ItemMedicineBinding;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    private final PatientMedicineFragment mainActivity;
    private final ArrayList<Medicine> collection;

    public MedicineAdapter(PatientMedicineFragment mainActivity, ArrayList<Medicine> collection) {
        this.mainActivity = mainActivity;
        this.collection = collection;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine, parent, false);

        return new MedicineViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine item = collection.get(position);

        if (item != null) {
            holder.binding.TextViewName.setText(item.getName());
            holder.binding.TextViewWeight.setText(item.getWeight());
            holder.binding.TextViewUnit.setText(item.getUnit());
            holder.binding.txtGia.setText(Integer.toString(item.getPrice()));
        }

        holder.binding.buttonAddToCard.setOnClickListener(v -> {
            mainActivity.insertToCardMedicine(item);
        });

        holder.binding.itemMedicine.setOnClickListener(v -> {
            mainActivity.handlerViewMedicineDetail(item);
        });
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        private ItemMedicineBinding binding;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemMedicineBinding.bind(itemView);
        }
    }
}
