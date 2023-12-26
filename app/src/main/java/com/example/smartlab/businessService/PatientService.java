package com.example.smartlab.businessService;

import androidx.annotation.NonNull;

import com.example.smartlab.businessObject.PatientInfo;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PatientService {

    private DatabaseReference reference;
    private static PatientService instance;

    public PatientService() {
        reference = FirebaseDatabase.getInstance().getReference("PatientInfo");
    }

    public static synchronized PatientService getInstance() {
        if (instance == null) {
            instance = new PatientService();
        }
        return instance;
    }

    public CompletableFuture<ReferenceInfo<PatientInfo>> isPhoneNumberExisted(String phoneNumberCheck) {

        CompletableFuture<ReferenceInfo<PatientInfo>> future = new CompletableFuture<>();

        ReferenceInfo<PatientInfo> patientReferenceInfo = new ReferenceInfo<>();

        reference.orderByChild("phoneNumber").equalTo(phoneNumberCheck).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientReferenceInfo.setStatus(ReferenceStatusEnum.SUCCESS);

                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        patientReferenceInfo.setData(childSnapshot.getValue(PatientInfo.class));
                    }
                }

                future.complete(patientReferenceInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                patientReferenceInfo.setStatus(ReferenceStatusEnum.ERROR);
                patientReferenceInfo.setMessage(error.getMessage());

                future.complete(patientReferenceInfo);
            }
        });

        return future;
    }

    public CompletableFuture<ReferenceInfo<PatientInfo>> Insert(PatientInfo infoInsert) {

        CompletableFuture<ReferenceInfo<PatientInfo>> future = new CompletableFuture<>();

        ReferenceInfo<PatientInfo> patientReferenceInfo = new ReferenceInfo<>();

        infoInsert.setKey(reference.push().getKey());

        reference.child(infoInsert.getKey()).setValue(infoInsert)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        patientReferenceInfo.setStatus(ReferenceStatusEnum.SUCCESS);
                        patientReferenceInfo.setData(infoInsert);
                        future.complete(patientReferenceInfo);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        patientReferenceInfo.setStatus(ReferenceStatusEnum.ERROR);
                        patientReferenceInfo.setMessage(e.getMessage());
                        future.complete(patientReferenceInfo);
                    }
                });

        return future;
    }

    public CompletableFuture<ReferenceInfo<PatientInfo>> UpdateInfo(PatientInfo updatedInfo) {
        CompletableFuture<ReferenceInfo<PatientInfo>> future = new CompletableFuture<>();

        if (updatedInfo.getKey() == null || updatedInfo.getKey().isEmpty()) {
            ReferenceInfo<PatientInfo> emptyKeyInfo = new ReferenceInfo<>();
            emptyKeyInfo.setStatus(ReferenceStatusEnum.ERROR);
            emptyKeyInfo.setMessage("PatientInfo không tồn tại Key để thực hiện Update");
            future.complete(emptyKeyInfo);
        } else {
            reference.child(updatedInfo.getKey()).setValue(updatedInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            ReferenceInfo<PatientInfo> updatedReferenceInfo = new ReferenceInfo<>();
                            updatedReferenceInfo.setStatus(ReferenceStatusEnum.SUCCESS);
                            updatedReferenceInfo.setData(updatedInfo);
                            future.complete(updatedReferenceInfo);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ReferenceInfo<PatientInfo> errorReferenceInfo = new ReferenceInfo<>();
                            errorReferenceInfo.setStatus(ReferenceStatusEnum.ERROR);
                            errorReferenceInfo.setMessage(e.getMessage());
                            future.complete(errorReferenceInfo);
                        }
                    });
        }

        return future;
    }

}
