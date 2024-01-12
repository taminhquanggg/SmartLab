package com.example.smartlab.businessService;

import androidx.annotation.NonNull;

import com.example.smartlab.businessObject.Department;
import com.example.smartlab.businessObject.ReferenceInfo;
import com.example.smartlab.businessObject.ReferenceStatusEnum;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.CompletableFuture;

public class DepartmentService {

    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Department");

    private static DepartmentService instance;

    public DepartmentService() {

    }

    public static synchronized DepartmentService getInstance() {
        if (instance == null) {
            instance = new DepartmentService();
        }
        return instance;
    }

    public CompletableFuture<ReferenceInfo<Department>> Insert(Department infoInsert) {

        CompletableFuture<ReferenceInfo<Department>> future = new CompletableFuture<>();

        ReferenceInfo<Department> departmentReferenceInfo = new ReferenceInfo<>();

        infoInsert.setDepartmentID(reference.push().getKey());

        reference.child(infoInsert.getDepartmentID()).setValue(infoInsert)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        departmentReferenceInfo.setStatus(ReferenceStatusEnum.SUCCESS);
                        departmentReferenceInfo.setData(infoInsert);
                        future.complete(departmentReferenceInfo);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        departmentReferenceInfo.setStatus(ReferenceStatusEnum.ERROR);
                        departmentReferenceInfo.setMessage(e.getMessage());
                        future.complete(departmentReferenceInfo);
                    }
                });

        return future;
    }

    public CompletableFuture<ReferenceInfo<Department>> Update(Department updatedInfo) {
        CompletableFuture<ReferenceInfo<Department>> future = new CompletableFuture<>();

        if (updatedInfo.getDepartmentID() == null || updatedInfo.getDepartmentID().isEmpty()) {
            ReferenceInfo<Department> emptyKeyInfo = new ReferenceInfo<>();
            emptyKeyInfo.setStatus(ReferenceStatusEnum.ERROR);
            emptyKeyInfo.setMessage("Department không tồn tại Key để thực hiện Update");
            future.complete(emptyKeyInfo);
        } else {
            reference.child(updatedInfo.getDepartmentID()).setValue(updatedInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            ReferenceInfo<Department> updatedReferenceInfo = new ReferenceInfo<>();
                            updatedReferenceInfo.setStatus(ReferenceStatusEnum.SUCCESS);
                            updatedReferenceInfo.setData(updatedInfo);
                            future.complete(updatedReferenceInfo);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ReferenceInfo<Department> errorReferenceInfo = new ReferenceInfo<>();
                            errorReferenceInfo.setStatus(ReferenceStatusEnum.ERROR);
                            errorReferenceInfo.setMessage(e.getMessage());
                            future.complete(errorReferenceInfo);
                        }
                    });
        }

        return future;
    }

}
