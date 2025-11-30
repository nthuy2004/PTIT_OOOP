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
    public Integer id;
    public Patient patient;
    public Integer status;
    public List<PrescriptionDetail> details;

    public Prescription(Integer id, Patient patient, Integer status) {
        this.id = id;
        this.patient = patient;
        this.status = status;
    }

    public void setDetails(List<PrescriptionDetail> details) {
        this.details = details;
    }
}