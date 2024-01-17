package com.example.smartlab.businessService;

import com.example.smartlab.businessObject.BookingHospital;
import com.example.smartlab.businessObject.MedicineCard;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MedicineCardService {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MedicineCard");
    private static MedicineCardService instance;

    public MedicineCardService() {

    }

    public static synchronized MedicineCardService getInstance() {
        if (instance == null) {
            instance = new MedicineCardService();
        }
        return instance;
    }

    public Task<ArrayList<MedicineCard>> getMedicineCardList(String PatientID) {

        ArrayList<MedicineCard> medicineCardList = new ArrayList<>();

        return reference.orderByChild("patientID").equalTo(PatientID)
                .get().continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                medicineCardList.add(childSnapshot.getValue(MedicineCard.class));
                            }
                        }

                        return Tasks.forResult(medicineCardList);
                    }
                    return null;
                });
    }

    public Task<ReferenceInfo<MedicineCard>> Insert(MedicineCard infoInsert) {
        infoInsert.setMedicineCardID(reference.push().getKey());
        return reference.child(infoInsert.getMedicineCardID()).setValue(infoInsert).continueWithTask(task -> {
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

    public Task<Boolean> Delete(String medicineCardID) {
        return reference.child(medicineCardID).removeValue()
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return Tasks.forResult(true);
                    } else {
                        return Tasks.forResult(false);
                    }
                });
    }

}

