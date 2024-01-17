package com.example.smartlab.businessObject;

public class MedicineCard {
    private String medicineCardID;
    private String patientID;
    private String medicineID;
    private String medicineName;

    private int price;

    public MedicineCard(String patientID, String medicineID, String medicineName, int price) {
        this.patientID = patientID;
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.price = price;
    }


    public MedicineCard() {
    }

    public String getMedicineCardID() {
        return medicineCardID;
    }

    public void setMedicineCardID(String medicineCardID) {
        this.medicineCardID = medicineCardID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
