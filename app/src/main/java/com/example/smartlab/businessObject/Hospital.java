package com.example.smartlab.businessObject;

import java.io.Serializable;

public class Hospital implements Serializable {
    private String hospitalID;
    private String hospitalName;
    private String address;
    private String hotline;

    public Hospital() {

    }

    public Hospital(Serializable hospital) {

    }

    public Hospital(Hospital hospital) {
        this.hospitalID = hospital.getHospitalID();
        this.hospitalName = hospital.getHospitalName();
        this.address = hospital.getAddress();
        this.hotline = hospital.getHotline();
    }

    public Hospital(String hospitalID, String hospitalName, String address, String hotline) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.address = address;
        this.hotline = hotline;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }
}