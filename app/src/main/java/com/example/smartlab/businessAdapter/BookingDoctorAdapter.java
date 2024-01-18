package com.example.smartlab.businessAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlab.R;
import com.example.smartlab.businessObject.BookingDoctor;
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessService.BookingDoctorService;
import com.example.smartlab.businessService.BookingHospitalService;
import com.example.smartlab.businessService.DoctorService;
import com.example.smartlab.businessService.HospitalService;
import com.example.smartlab.businessView.businessActivity.PatientScheduleDoctorActivity;
import com.example.smartlab.businessView.businessActivity.PatientScheduleHospitalActivity;
import com.example.smartlab.databinding.ItemScheduleDoctorBinding;
import com.example.smartlab.databinding.ItemScheduleHospitalBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BookingDoctorAdapter extends RecyclerView.Adapter<BookingDoctorAdapter.BookingDoctorViewHolder> {

    private final PatientScheduleDoctorActivity mainActivity;
    private final ArrayList<BookingDoctor> collection;

    public BookingDoctorAdapter(PatientScheduleDoctorActivity mainActivity, ArrayList<BookingDoctor> collection) {
        this.mainActivity = mainActivity;
        this.collection = collection;
    }

    @NonNull
    @Override
    public BookingDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule_doctor, parent, false);

        return new BookingDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDoctorViewHolder holder, int position) {
        BookingDoctor item = collection.get(position);

        if (item != null) {

            DoctorService.getInstance().getDoctorInfo(item.getDoctorID())
                    .addOnSuccessListener(doctor -> {
                        if (doctor != null) {
                            holder.binding.txtDoctorName.setText(doctor.getDoctorName());
                            holder.binding.txtHospitalName.setText(doctor.getHospitalName());
                            holder.binding.txtDepartment.setText(doctor.getDepartmentName());
                        }

                    }).addOnFailureListener(e -> {
                        Log.e("abc", e.getMessage());
                    });

            holder.binding.txtHour.setText(item.getHourBooking());

            holder.binding.txtDayOfMonth.setText(item.getDateBooking());

            holder.binding.buttonCancleSchedule.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

                builder.setTitle("Xác nhận hủy hẹn");
                builder.setMessage("Bạn có chắc chắn muốn hủy hẹn với bác sĩ?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        BookingDoctorService.getInstance().Delete(item.getBookingID())
                                .addOnSuccessListener(success -> {
                                    if (success) {
                                        mainActivity.bindingScheduleDoctor();
                                    }
                                });
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            });


        }
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class BookingDoctorViewHolder extends RecyclerView.ViewHolder {
        private final ItemScheduleDoctorBinding binding;

        public BookingDoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemScheduleDoctorBinding.bind(itemView);
        }
    }
}