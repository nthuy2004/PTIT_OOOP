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
    private Integer id;
    private Patient patient;
    private Staff doctor;
    private String diagnostic;
    private String plan;
    private String status;
    private LocalDateTime time;
    private List<ServiceUsage> serviceUsages;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Staff doctor) {
        this.doctor = doctor;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    
    
}