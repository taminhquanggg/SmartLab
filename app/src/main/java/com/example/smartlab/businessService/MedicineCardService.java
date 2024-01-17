package com.example.smartlab.businessService;

import com.example.smartlab.businessObject.Medicine;
import com.example.smartlab.businessObject.PatientCardMedicine;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MedicineCardService {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicine");
    private static MedicineCardService instance;
    public MedicineCardService() {

    }
    public static synchronized MedicineCardService getInstance() {
        if (instance == null) {
            instance = new MedicineCardService();
        }
        return instance;
    }
    public Task<ArrayList<PatientCardMedicine>> getMedicineCardList() {

        ArrayList<PatientCardMedicine> medicineCardList = new ArrayList<>();

        return reference.get().continueWithTask(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        medicineCardList.add(childSnapshot.getValue(PatientCardMedicine.class));
                    }
                }

                return Tasks.forResult(medicineCardList);
            }
            else {
                return Tasks.forResult(null);
            }
        });
    }
    public Task<ReferenceInfo<PatientCardMedicine>> insert (PatientCardMedicine infoInsert ){
        infoInsert.setMedicineCardID(reference.push().getKey());
        return  reference.child(infoInsert.getMedicineCardID()).setValue(infoInsert).continueWithTask(task -> {
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

