/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.dao.MedicalRecordDAO;
import com.ptit.dental.model.entity.MedicalRecord;
import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.utils.Injector;
import com.ptit.dental.view.PatientDetailDialog;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller for Patient Medical Record Detail Dialog
 */
public class PatientDetailController extends BaseController<PatientDetailDialog> {

    private Patient patient;
    private MedicalRecordDAO mDAO;
    private List<MedicalRecord> medicalRecords;

    public PatientDetailController(PatientDetailDialog view, Patient patient) {
        super(view);
        this.patient = patient;
        this.mDAO = Injector.get(MedicalRecordDAO.class);
        initData();
        initController();
    }

    private void initData() {
        // Set patient info - read-only fields
        view.txtFullname.setText(patient.getFullname());
        view.txtGender.setText(patient.getGender().toString());
        view.txtPhone.setText(patient.getPhone());
        view.txtAddress.setText(patient.getAddress());

        // Load medical records
        try {
            medicalRecords = mDAO.getByPatientId(patient.getId());
            if (!medicalRecords.isEmpty()) {
                MedicalRecord latestRecord = medicalRecords.get(0);
                // Populate with latest medical record data
                if (latestRecord.getDoctor() != null) {
                    view.cbDoctorName.addItem(latestRecord.getDoctor().getFullname());
                }
                view.txtVisitDate.setText(latestRecord.getTime().toString());
                view.cbTreatmentStatus.setSelectedItem(latestRecord.getStatus());
                view.txtDiagnosis.setText(latestRecord.getDiagnostic());
                view.txtTreatmentPlan.setText(latestRecord.getPlan());
                renderServices(latestRecord);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initController() {
        view.btnSave.addActionListener(e -> saveRecord());
        view.btnCancel.addActionListener(e -> view.dispose());
    }

    private void saveRecord() {
        // TODO: Implement save logic
        view.dispose();
    }

    private void renderServices(MedicalRecord record) {
        DefaultTableModel model = (DefaultTableModel) view.tblServices.getModel();
        model.setRowCount(0);

        // Add sample service data - adjust based on your MedicalRecord structure
        model.addRow(new Object[] {
                "Service Name",
                "1",
                "Note",
                "100,000 VND"
        });
    }
}
