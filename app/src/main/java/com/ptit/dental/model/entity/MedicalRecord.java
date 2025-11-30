/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Administrator
 */

public class MedicalRecord {
    public Integer id;
    public Patient patient;
    public Staff doctor;
    public String diagnostic;
    public String plan;
    public String status;
    public LocalDateTime time;
    public List<ServiceUsage> serviceUsages;

    public MedicalRecord(Integer id,
            Patient patient,
            Staff doctor,
            String diagnostic,
            String plan,
            String status,
            LocalDateTime time) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.diagnostic = diagnostic;
        this.plan = plan;
        this.status = status;
        this.time = time;
    }

    public void setServiceUsages(List<ServiceUsage> serviceUsages) {
        this.serviceUsages = serviceUsages;
    }

    public Integer getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Staff getDoctor() {
        return doctor;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public String getPlan() {
        return plan;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public List<ServiceUsage> getServiceUsages() {
        return serviceUsages;
    }
}