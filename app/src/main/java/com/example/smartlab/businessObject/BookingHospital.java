package com.example.smartlab.businessObject;

import java.io.Serializable;
import java.util.Calendar;

public class BookingHospital implements Serializable {
    private String bookingID;
    private String patientID;
    private String hospitalID;
    private String reason;
    private String dateBooking;
    private String hourBooking;

    public BookingHospital(String bookingID, String patientID, String hospitalID, String reason, String dateBooking, String hourBooking) {
        this.bookingID = bookingID;
        this.patientID = patientID;
        this.hospitalID = hospitalID;
        this.reason = reason;
        this.dateBooking = dateBooking;
        this.hourBooking = hourBooking;
    }

    public BookingHospital() {
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Calendar getCalendarBooking() {
        Calendar calendar = Calendar.getInstance();

        String[] calendarDate = this.dateBooking.split("/");
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(calendarDate[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(calendarDate[1]) - 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(calendarDate[2]));

        String[] calendarHour = this.hourBooking.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(calendarHour[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(calendarHour[1]));

        return calendar;
    }
}
