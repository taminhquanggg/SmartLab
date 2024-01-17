package com.example.smartlab.businessObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Field;

public class Patient implements Serializable {
    private String patientID;
    private String phoneNumber;
    private String password;
    private String patientName;
    private String dateOfBirth;
    private String sex;
    private String address;
    private String verificationId;

    public Patient() {

    }
    public Patient(Serializable patientInfo) {
    }
    public Patient(Patient patient) {
        this.patientID = patient.getPatientID();
        this.phoneNumber = patient.getPhoneNumber();
        this.password = patient.getPassword();
        this.patientName = patient.getPatientName();
        this.dateOfBirth = patient.getDateOfBirth();
        this.sex = patient.getSex();
        this.address = patient.getAddress();
        this.verificationId = patient.getVerificationId();
    }
    public Patient(String patientID, String phoneNumber, String password, String patientName, String dateOfBirth, String sex, String address, String verificationId) {
        this.patientID = patientID;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.patientName = patientName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.address = address;
        this.verificationId = verificationId;
    }
    public String getPatientID() {
        return patientID;
    }
    public void setPatientID(String patientID) {
        this.patientID = patientID;
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
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
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
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(this);
                if (value != null) {
                    map.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }
}
