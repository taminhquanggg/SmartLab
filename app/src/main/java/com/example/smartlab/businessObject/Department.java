package com.example.smartlab.businessObject;

import java.io.Serializable;

public class Department implements Serializable {
    private String departmentID;
    private String departmentName;

    public Department() {

    }

    public Department(Serializable department) {

    }


    public Department(String departmentID, String departmentName) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
    }

    public Department(Department department) {
        this.departmentID = department.getDepartmentID();
        this.departmentName = department.getDepartmentName();
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
