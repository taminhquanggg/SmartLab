package com.example.smartlab.businessService;

import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.Medicine;
import com.example.smartlab.businessObject.Patient;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MedicineService {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicine");
    private static MedicineService instance;
    public MedicineService() {

    }
    public static synchronized MedicineService getInstance() {
        if (instance == null) {
            instance = new MedicineService();
        }
        return instance;
    }
    public Task<ArrayList<Medicine>> getMedicineList() {

        ArrayList<Medicine> medicineList = new ArrayList<>();

        return reference.get().continueWithTask(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        medicineList.add(childSnapshot.getValue(Medicine.class));
                    }
                }

                return Tasks.forResult(medicineList);
            }
            else {
                return Tasks.forResult(null);
            }
        });
    }
    public Task<ReferenceInfo<Medicine>> insert (Medicine infoInsert ){
        infoInsert.setMedicineID(reference.push().getKey());
        return  reference.child(infoInsert.getMedicineID()).setValue(infoInsert).continueWithTask(task -> {
            if (task.isSuccessful()){
                return Tasks.forResult (new ReferenceInfo<>
                (
                        ReferenceStatusEnum.SUCCESS,
                        infoInsert,
                            null
                        ));
            } else {
                return Tasks.forResult(new ReferenceInfo<>
                        (
                                ReferenceStatusEnum.ERROR,
                                    null   ,
                                task.getException().getMessage()
                        ));
            }
        });
    }

}

