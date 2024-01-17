package com.example.smartlab.businessAdapter;

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
import com.example.smartlab.businessView.businessFragment.PatientHomeFragment;
import com.example.smartlab.databinding.ItemDoctorBinding;
import com.example.smartlab.databinding.ItemDoctorFragmentPatientHomeBinding;

import java.util.ArrayList;

public class PatientBookDoctorAdapter extends RecyclerView.Adapter<PatientBookDoctorAdapter.PatientBookDoctorViewHolder> {
    private final PatientHomeFragment mainFragment;
    private final ArrayList<Doctor> collection;

    public PatientBookDoctorAdapter(PatientHomeFragment mainFragment, ArrayList<Doctor> collection) {
        this.mainFragment = mainFragment;
        this.collection = collection;
    }

    @NonNull
    @Override
    public PatientBookDoctorAdapter.PatientBookDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor_fragment_patient_home, parent, false);

        return new PatientBookDoctorAdapter.PatientBookDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientBookDoctorAdapter.PatientBookDoctorViewHolder holder, int position) {
        Doctor item = collection.get(position);

        holder.binding.txtDoctorName.setText(item.getDoctorName());
        holder.binding.txtDepartment.setText(item.getDepartmentName());
        holder.binding.txtHospital.setText(item.getHospitalName());

        if (item.getAvatar() != null && !item.getAvatar().isEmpty()) {
            byte[] decodedString = Base64.decode(item.getAvatar(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.binding.imageDoctor.setImageBitmap(decodedByte);
        }

        holder.binding.layoutButtonDoctor.setOnClickListener(v -> {

        });

    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class PatientBookDoctorViewHolder extends RecyclerView.ViewHolder {
        private final ItemDoctorBinding binding;

        public PatientBookDoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDoctorBinding.bind(itemView);
        }
    }

}