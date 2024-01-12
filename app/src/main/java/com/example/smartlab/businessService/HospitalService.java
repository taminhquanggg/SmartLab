package com.example.smartlab.businessService;

import androidx.annotation.NonNull;

import com.example.smartlab.businessObject.Hospital;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<ReferenceInfo<Hospital>> Insert(Hospital infoInsert) {

        CompletableFuture<ReferenceInfo<Hospital>> future = new CompletableFuture<>();

        ReferenceInfo<Hospital> hospitalReferenceInfo = new ReferenceInfo<>();

        infoInsert.setHospitalID(reference.push().getKey());

        reference.child(infoInsert.getHospitalID()).setValue(infoInsert)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        hospitalReferenceInfo.setStatus(ReferenceStatusEnum.SUCCESS);
                        hospitalReferenceInfo.setData(infoInsert);
                        future.complete(hospitalReferenceInfo);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hospitalReferenceInfo.setStatus(ReferenceStatusEnum.ERROR);
                        hospitalReferenceInfo.setMessage(e.getMessage());
                        future.complete(hospitalReferenceInfo);
                    }
                });

        return future;
    }

    public CompletableFuture<ReferenceInfo<Hospital>> Update(Hospital updatedInfo) {
        CompletableFuture<ReferenceInfo<Hospital>> future = new CompletableFuture<>();

        if (updatedInfo.getHospitalID() == null || updatedInfo.getHospitalID().isEmpty()) {
            ReferenceInfo<Hospital> emptyKeyInfo = new ReferenceInfo<>();
            emptyKeyInfo.setStatus(ReferenceStatusEnum.ERROR);
            emptyKeyInfo.setMessage("Hospital không tồn tại Key để thực hiện Update");
            future.complete(emptyKeyInfo);
        } else {
            reference.child(updatedInfo.getHospitalID()).setValue(updatedInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            ReferenceInfo<Hospital> updatedReferenceInfo = new ReferenceInfo<>();
                            updatedReferenceInfo.setStatus(ReferenceStatusEnum.SUCCESS);
                            updatedReferenceInfo.setData(updatedInfo);
                            future.complete(updatedReferenceInfo);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ReferenceInfo<Hospital> errorReferenceInfo = new ReferenceInfo<>();
                            errorReferenceInfo.setStatus(ReferenceStatusEnum.ERROR);
                            errorReferenceInfo.setMessage(e.getMessage());
                            future.complete(errorReferenceInfo);
                        }
                    });
        }

        return future;
    }

}
