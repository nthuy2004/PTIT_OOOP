package model;

import java.util.Date;
import java.util.List;

public class MedicalRecord {
    private String recordId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String treatmentPlan;
    private String notes;
    private Date dateCreated;
    private List<Service> services;
    private Invoice invoice;

    public MedicalRecord(String recordId, String patientId, String doctorId,
                         String diagnosis, String treatmentPlan, String notes,
                         Date dateCreated, List<Service> services, Invoice invoice) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.notes = notes;
        this.dateCreated = dateCreated;
        this.services = services;
        this.invoice = invoice;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
