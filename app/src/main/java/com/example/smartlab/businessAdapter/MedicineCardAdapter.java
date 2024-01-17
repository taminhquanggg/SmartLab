package com.example.smartlab.businessAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.MedicineCard;
import com.example.smartlab.businessService.MedicineCardService;
import com.example.smartlab.businessView.businessActivity.PatientMedicineCartActivity;
import com.example.smartlab.databinding.ItemMedicineCartBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MedicineCardAdapter extends RecyclerView.Adapter<MedicineCardAdapter.MedicineCardViewHolder> {
    private final PatientMedicineCartActivity mainActivity;
    private final ArrayList<MedicineCard> collection;

    public MedicineCardAdapter(PatientMedicineCartActivity mainActivity, ArrayList<MedicineCard> collection) {
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
        MedicineCard item = collection.get(position);
        if (item != null) {
            holder.binding.TextViewName.setText(item.getMedicineName());
            holder.binding.TextViewGiatien.setText(Integer.toString(item.getPrice()));

            holder.binding.buttonDelete.setOnClickListener(v -> {
                MedicineCardService.getInstance().Delete(item.getMedicineCardID()).addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean) {
                            mainActivity.bindingListCard();
                        }
                    }
                });
            });
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
