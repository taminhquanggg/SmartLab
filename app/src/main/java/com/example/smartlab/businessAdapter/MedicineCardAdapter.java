package com.example.smartlab.businessAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Medicine;
import com.example.smartlab.businessObject.PatientCardMedicine;
import com.example.smartlab.businessView.businessActivity.PatientMedicineCartActivity;
import com.example.smartlab.databinding.ItemMedicineBinding;
import com.example.smartlab.databinding.ItemMedicineCartBinding;

import java.util.ArrayList;

public class MedicineCardAdapter extends RecyclerView.Adapter<MedicineCardAdapter.MedicineCardViewHolder> {
        private final PatientMedicineCartActivity mainActivity ;
        private final ArrayList<PatientCardMedicine> collection ;

    public MedicineCardAdapter(PatientMedicineCartActivity mainActivity, ArrayList<PatientCardMedicine> collection) {
        this.mainActivity = mainActivity;
        this.collection = collection;
    }

    @NonNull
    @Override
    public MedicineCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine_cart, parent, false);

        return new MedicineCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineCardViewHolder holder, int position) {
        PatientCardMedicine item = collection.get(position);
        if (item != null){
            holder.binding.TextViewParadol.setText(item.getName());
            holder.binding.textViewSoluong.setText(Integer.toString(item.getSoluong()));
            holder.binding.textViewSoluongmua.setText(Integer.toString(item.getSoluongMua()));
            holder.binding.TextViewGiatien.setText(Integer.toString(item.getGia()));

        }
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class MedicineCardViewHolder extends RecyclerView.ViewHolder {
        private ItemMedicineCartBinding binding;

        public MedicineCardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemMedicineCartBinding.bind(itemView);
        }
    }
}
