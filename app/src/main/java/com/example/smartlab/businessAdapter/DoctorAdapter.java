package com.example.smartlab.businessAdapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Doctor;
import com.example.smartlab.businessView.businessActivity.PatientBookDoctorActivity;
import com.example.smartlab.businessView.businessActivity.PatientScheduleHospitalDetailActivity;
import com.example.smartlab.businessView.businessActivity.PatientViewDoctorDetailActivity;
import com.example.smartlab.businessView.businessFragment.PatientHomeFragment;
import com.example.smartlab.databinding.ItemDoctorBinding;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.PatientViewHolder> {
    private final PatientBookDoctorActivity mainFragment;
    private final ArrayList<Doctor> collection;

    public DoctorAdapter(PatientBookDoctorActivity mainFragment, ArrayList<Doctor> collection) {
        this.mainFragment = mainFragment;
        this.collection = collection;
    }

    @NonNull
    @Override
    public DoctorAdapter.PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor, parent, false);

        return new DoctorAdapter.PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.PatientViewHolder holder, int position) {
        Doctor item = collection.get(position);

        holder.binding.txtDoctorName.setText(item.getDoctorName());
        holder.binding.txtDepartment.setText(item.getDepartmentName());
        holder.binding.txtHospital.setText(item.getHospitalName());

        holder.binding.buttonViewProfile.setOnClickListener(v -> {
            mainFragment.handlerViewDetailDoctor(item);
        });

        holder.binding.buttonBooking.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        private final ItemDoctorBinding binding;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDoctorBinding.bind(itemView);
        }
    }

}