/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

/**
 *
 * @author Administrator
 */
import java.util.List;

public class Prescription {
    private Integer id;
    private Patient patient;
    private Integer status;
    private List<PrescriptionDetail> details;

    public Prescription(Integer id, Patient patient, Integer status) {
        this.id = id;
        this.patient = patient;
        this.status = status;
    }

    public void setDetails(List<PrescriptionDetail> details) {
        this.details = details;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    
}