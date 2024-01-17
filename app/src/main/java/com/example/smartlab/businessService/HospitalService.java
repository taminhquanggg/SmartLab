package com.example.smartlab.businessService;

import com.example.smartlab.businessObject.Hospital;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HospitalService {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hospital");
    private static HospitalService instance;

    public HospitalService() {

    }

    public static synchronized HospitalService getInstance() {
        if (instance == null) {
            instance = new HospitalService();
        }
        return instance;
    }

    public Task<ArrayList<Hospital>> getHospitalList() {

        ArrayList<Hospital> hospitalList = new ArrayList<>();

        return reference.get().continueWithTask(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        hospitalList.add(childSnapshot.getValue(Hospital.class));
                    }
                }

                return Tasks.forResult(hospitalList);
            } else {
                return Tasks.forResult(null);
            }
        });
    }

    public Task<Hospital> getHospitalInfo(String hospitalID) {
        return reference.orderByChild("hospitalID").equalTo(hospitalID)
                .get().continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();

                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                return Tasks.forResult(childSnapshot.getValue(Hospital.class));
                            }
                        }
                    }
                    return null;
                });
    }

}
