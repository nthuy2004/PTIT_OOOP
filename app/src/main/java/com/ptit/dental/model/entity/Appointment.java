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
}
