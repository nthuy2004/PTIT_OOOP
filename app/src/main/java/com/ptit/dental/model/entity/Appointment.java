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
    public Integer id;
    public LocalDateTime time;
    public String reason;
    public Patient patient;
    public Staff doctor;

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

    // Convenience getters used by older UI/controller code
    public Integer getId() {
        return id;
    }

    public String getPatientName() {
        return patient != null ? patient.getFullname() : "";
    }

    public Date getDate() {
        if (time == null)
            return null;
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getTime() {
        return time != null ? time.toLocalTime().toString() : "";
    }

    public String getService() {
        return reason;
    }

    public String getNote() {
        return "";
    }
}
