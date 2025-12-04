/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

/**
 *
 * @author Administrator
 */
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Appointment {
    private Integer id;
    private LocalDateTime time;
    private String reason;
    private Patient patient;
    private Staff doctor;

    public Appointment(Integer id,
            LocalDateTime time,
            String reason,
            Patient patient,
            Staff doctor) {
        this.id = id;
        this.time = time;
        this.reason = reason;
        this.patient = patient;
        this.doctor = doctor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getDoctor() {
        return doctor;
    }

    public void setDoctor(Staff doctor) {
        this.doctor = doctor;
    }

    public Date getDate() {
        if (time == null)
            return null;
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getTimeOfDatetime() {
        return time != null ? time.toLocalTime().toString() : "";
    }
}
