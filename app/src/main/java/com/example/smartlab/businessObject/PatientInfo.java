package com.example.smartlab.businessObject;

import java.io.Serializable;
import java.util.Date;

public class PatientInfo implements Serializable {
    private String key;
    private String phoneNumber;
    private String password;
    private String patientName;
    private Date dateOfBirth;
    private String sex;
    private String address;
    private String verificationId;

    public PatientInfo(Serializable patientInfo) {
    }

    public PatientInfo(PatientInfo patientInfo) {
        this.key = patientInfo.getKey();
        this.phoneNumber = patientInfo.getPhoneNumber();
        this.password = patientInfo.getPassword();
        this.patientName = patientInfo.getPatientName();
        this.dateOfBirth = patientInfo.getDateOfBirth();
        this.sex = patientInfo.getSex();
        this.address = patientInfo.getAddress();
        this.verificationId = patientInfo.getVerificationId();
    }

    public PatientInfo(String key, String phoneNumber, String password, String patientName, Date dateOfBirth, String sex, String address, String verificationId) {
        this.key = key;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.patientName = patientName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.address = address;
        this.verificationId = verificationId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }
}
