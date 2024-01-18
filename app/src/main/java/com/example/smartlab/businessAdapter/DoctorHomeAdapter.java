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
import com.example.smartlab.databinding.ItemDoctorFragmentPatientHomeBinding;

import java.util.ArrayList;

public class DoctorHomeAdapter extends RecyclerView.Adapter<DoctorHomeAdapter.DoctorHomeViewHolder> {

    private final PatientHomeFragment mainFragment;
    private final ArrayList<Doctor> collection;

    public DoctorHomeAdapter(PatientHomeFragment mainFragment, ArrayList<Doctor> collection) {
        this.mainFragment = mainFragment;
        this.collection = collection;
    }

    @NonNull
    @Override
    public DoctorHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor_fragment_patient_home, parent, false);

        return new DoctorHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHomeViewHolder holder, int position) {
        Doctor item = collection.get(position);

        holder.binding.txtDoctorName.setText(item.getDoctorName());
        holder.binding.txtDepartment.setText(item.getDepartmentName());
        holder.binding.txtHospital.setText(item.getHospitalName());

        if (item.getAvatar() != null && !item.getAvatar().isEmpty()) {
            byte[] decodedString = Base64.decode(item.getAvatar(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.binding.imageAvatarDoctor.setImageBitmap(decodedByte);
        }

        holder.binding.buttonOrderDoctorTrending.setOnClickListener(v -> {
            mainFragment.handlerViewDetailDoctor(item);
        });

    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class DoctorHomeViewHolder extends RecyclerView.ViewHolder {
        private final ItemDoctorFragmentPatientHomeBinding binding;

        public DoctorHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDoctorFragmentPatientHomeBinding.bind(itemView);
        }
    }

}
