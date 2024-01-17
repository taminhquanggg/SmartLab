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
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessService.BookingHospitalService;
import com.example.smartlab.businessService.HospitalService;
import com.example.smartlab.businessView.businessActivity.PatientScheduleHospitalActivity;
import com.example.smartlab.databinding.ItemScheduleHospitalBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleHospitalAdapter extends RecyclerView.Adapter<ScheduleHospitalAdapter.ScheduleHospitalViewHolder> {

    private final PatientScheduleHospitalActivity mainActivity;
    private final ArrayList<BookingHospital> collection;

    public ScheduleHospitalAdapter(PatientScheduleHospitalActivity mainActivity, ArrayList<BookingHospital> collection) {
        this.mainActivity = mainActivity;
        this.collection = collection;
    }

    @NonNull
    @Override
    public ScheduleHospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule_hospital, parent, false);

        return new ScheduleHospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHospitalViewHolder holder, int position) {
        BookingHospital item = collection.get(position);

        if (item != null) {

            HospitalService.getInstance().getHospitalInfo(item.getHospitalID())
                    .addOnSuccessListener(hospital -> {
                        if (hospital != null) {
                            holder.binding.txtHospitalName.setText(hospital.getHospitalName());
                            holder.binding.txtHospitalAddress.setText(hospital.getAddress());
                        }

                    }).addOnFailureListener(e -> {
                        Log.e("abc", e.getMessage());
                    });

            holder.binding.txtHour.setText(item.getHourBooking());

            Calendar dayBook = item.getCalendarBooking();

            String dayofweek = dayBook.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("vi", "VN"));

            holder.binding.txtDayOfWeek.setText(item.getCalendarBooking()
                    .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("vi", "VN")));
            holder.binding.txtDayOfMonth.setText(item.getDateBooking());

            holder.binding.buttonViewScheduleDetail.setOnClickListener(v -> {
                mainActivity.handleViewScheduleDetail(item);
            });

            holder.binding.buttonCancleSchedule.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

                builder.setTitle("Xác nhận hủy hẹn");
                builder.setMessage("Bạn có chắc chắn muốn hủy hẹn?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        BookingHospitalService.getInstance().Delete(item.getBookingID())
                                .addOnSuccessListener(success -> {
                                    if (success) {
                                        mainActivity.bindingScheduleHospital();
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

    public static class ScheduleHospitalViewHolder extends RecyclerView.ViewHolder {
        private final ItemScheduleHospitalBinding binding;

        public ScheduleHospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemScheduleHospitalBinding.bind(itemView);
        }
    }
}
