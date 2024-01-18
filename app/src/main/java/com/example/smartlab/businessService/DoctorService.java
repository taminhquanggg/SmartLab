package com.example.smartlab.businessService;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.smartlab.businessObject.Doctor;
import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DoctorService {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctor");
    private static DoctorService instance;

    public DoctorService() {

    }

    public static synchronized DoctorService getInstance() {
        if (instance == null) {
            instance = new DoctorService();
        }
        return instance;
    }

    public Task<ArrayList<Doctor>> getDoctorList() {

        ArrayList<Doctor> doctorList = new ArrayList<>();

        return reference.get().continueWithTask(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        doctorList.add(childSnapshot.getValue(Doctor.class));
                    }
                }

                return Tasks.forResult(doctorList);
            } else {
                return Tasks.forResult(null);
            }
        });
    }

    public Task<ReferenceInfo<Doctor>> Insert(Doctor infoInsert) {

        infoInsert.setDoctorID(reference.push().getKey());

        return reference.child(infoInsert.getDoctorID()).setValue(infoInsert)
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

    public Task<Doctor> getDoctorInfo(String doctorID) {
        return reference.orderByChild("doctorID").equalTo(doctorID)
                .get().continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();

                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                return Tasks.forResult(childSnapshot.getValue(Doctor.class));
                            }
                        }
                    }
                    return null;
                });
    }
}

