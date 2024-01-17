package com.example.smartlab.businessAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessView.businessActivity.PatientHospitalMapsActivity;
import com.example.smartlab.databinding.ItemHospitalFragmentPatientHospitalMapsBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HospitalMapAdapter extends RecyclerView.Adapter<HospitalMapAdapter.HospitalMapViewHolder> {

    private final PatientHospitalMapsActivity mainActivity;
    private final ArrayList<Hospital> collection;

    public HospitalMapAdapter(PatientHospitalMapsActivity mainActivity, ArrayList<Hospital> collection) {
        this.mainActivity = mainActivity;
        this.collection = collection;
    }

    @NonNull
    @Override
    public HospitalMapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hospital_fragment_patient_hospital_maps, parent, false);

        return new HospitalMapAdapter.HospitalMapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalMapViewHolder holder, int position) {
        Hospital item = collection.get(position);

        if (item != null) {
            holder.binding.textHospitalName.setText(item.getHospitalName());
            holder.binding.textHospitalAddress.setText(item.getAddress());
        }

        holder.binding.buttonBookHospital.setOnClickListener(v -> {
            mainActivity.handlerPatientBookHospital(item);
        });

        holder.binding.itemBottomSheet.setOnClickListener(v -> {
            mainActivity.setHospitalInfo(item);

            mainActivity.setVisibleHospitalMap(View.INVISIBLE);
            mainActivity.setVisibleInfoHospital(View.VISIBLE);

            mainActivity.MoveToLocation(item.getLatitude(), item.getLongitude(), item.getHospitalName());
        });


    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class HospitalMapViewHolder extends RecyclerView.ViewHolder {
        private final ItemHospitalFragmentPatientHospitalMapsBinding binding;

        public HospitalMapViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemHospitalFragmentPatientHospitalMapsBinding.bind(itemView);
        }
    }
}
