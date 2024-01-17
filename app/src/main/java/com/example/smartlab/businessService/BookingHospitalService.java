package com.example.smartlab.businessService;

import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookingHospitalService {

    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BookingHospital");

    private static BookingHospitalService instance;

    public static synchronized BookingHospitalService getInstance() {
        if (instance == null) {
            instance = new BookingHospitalService();
        }
        return instance;
    }

    public Task<ReferenceInfo<BookingHospital>> Insert(BookingHospital infoInsert) {

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

    public Task<ReferenceInfo<Patient>> isPhoneNumberExisted(String phoneNumberCheck) {

        return reference.orderByChild("phoneNumber").equalTo(phoneNumberCheck)
                .get().continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        ReferenceInfo<Patient> patientReferenceInfo = new ReferenceInfo<>();
                        patientReferenceInfo.setStatus(ReferenceStatusEnum.SUCCESS);

                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                patientReferenceInfo.setData(childSnapshot.getValue(Patient.class));
                                break;
                            }
                        }

                        return Tasks.forResult(patientReferenceInfo);
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

    public Task<ArrayList<BookingHospital>> getPatientBookingHospitalList(String patientID) {

        ArrayList<BookingHospital> bookingList = new ArrayList<>();

        return reference.orderByChild("patientID").equalTo(patientID)
                .get().continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                bookingList.add(childSnapshot.getValue(BookingHospital.class));
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
