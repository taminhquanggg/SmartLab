package com.example.smartlab.businessObject;

public class BookingDoctor {
    private String bookingID;
    private String doctorID;
    private String patientID;
    private String dateBooking;
    private String hourBooking;

    public BookingDoctor() {
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public String getHourBooking() {
        return hourBooking;
    }

    public void setHourBooking(String hourBooking) {
        this.hourBooking = hourBooking;
    }
}
