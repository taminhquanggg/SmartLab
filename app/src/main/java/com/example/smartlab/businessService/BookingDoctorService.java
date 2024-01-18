package com.example.smartlab.businessService;

import com.example.smartlab.businessObject.BookingDoctor;
import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookingDoctorService {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BookingDoctor");

    private static BookingDoctorService instance;

    public static synchronized BookingDoctorService getInstance() {
        if (instance == null) {
            instance = new BookingDoctorService();
        }
        return instance;
    }

    public Task<ReferenceInfo<BookingDoctor>> Insert(BookingDoctor infoInsert) {

        infoInsert.setBookingID(reference.push().getKey());

        return reference.child(infoInsert.getBookingID()).setValue(infoInsert)
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return Tasks.forResult(new ReferenceInfo<>
                                (
                                        ReferenceStatusEnum.SUCCESS,
                                        infoInsert,
                                        null
                                ));
                    } else {
                        return Tasks.forResult(new ReferenceInfo<>
                                (
                                        ReferenceStatusEnum.ERROR,
                                        null,
                                        task.getException().getMessage()
                                ));
                    }
                });
    }

    public Task<ArrayList<BookingDoctor>> getPatientBookingDoctorList(String patientID) {

        ArrayList<BookingDoctor> bookingList = new ArrayList<>();

        return reference.orderByChild("patientID").equalTo(patientID)
                .get().continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                bookingList.add(childSnapshot.getValue(BookingDoctor.class));
                            }
                        }

                        return Tasks.forResult(bookingList);
                    }
                    return null;
                });
    }

    public Task<Boolean> Delete(String bookingID) {
        return reference.child(bookingID).removeValue()
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return Tasks.forResult(true);
                    } else {
                        return Tasks.forResult(false);
                    }
                });
    }



}
