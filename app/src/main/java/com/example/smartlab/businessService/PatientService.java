package com.example.smartlab.businessService;

import androidx.annotation.NonNull;

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

import java.util.HashMap;
import java.util.Map;

public class PatientService {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patient");
    private static PatientService instance;

    public PatientService() {

    }

    public static synchronized PatientService getInstance() {
        if (instance == null) {
            instance = new PatientService();
        }
        return instance;
    }

    //CRUD
    public Task<ReferenceInfo<Patient>> Insert(Patient infoInsert) {

        infoInsert.setPatientID(reference.push().getKey());

        return reference.child(infoInsert.getPatientID()).setValue(infoInsert)
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

    public Task<ReferenceInfo<Patient>> Update(String patientID, Patient updatedInfo) {

        if (updatedInfo.getPatientID() == null || updatedInfo.getPatientID().isEmpty()) {
            return Tasks.forResult(new ReferenceInfo<>(
                    ReferenceStatusEnum.ERROR,
                    updatedInfo,
                    "PatientInfo không tồn tại Key để thực hiện Update"
            ));
        }

        return reference.child(patientID).updateChildren(updatedInfo.toMap())
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return Tasks.forResult(new ReferenceInfo<>
                                (
                                        ReferenceStatusEnum.SUCCESS,
                                        updatedInfo,
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

    //Service
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

}