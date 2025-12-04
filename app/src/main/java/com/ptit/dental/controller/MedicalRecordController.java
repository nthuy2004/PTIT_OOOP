package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.view.MedicalRecordView;

public class MedicalRecordController extends BaseController<MedicalRecordView> {
    public MedicalRecordController(MedicalRecordView view) {
        super(view);
        initListeners();
    }

    private void initListeners() {
        view.saveButton.addActionListener(e -> {
            // TODO: Implement save logic
            String patientName = view.namePatientField.getText();
            String doctor = (String) view.doctorComboBox.getSelectedItem();
            String diseaseType = (String) view.diseaseTypeComboBox.getSelectedItem();
            String diagnostic = view.diagnosticArea.getText();
            String plan = view.planArea.getText();
            String status = (String) view.statusComboBox.getSelectedItem();
            java.util.Date date = view.dateChooser.getDate();
            // Save to database
        });

        view.cancelButton.addActionListener(e -> {
            view.dispose();
        });
    }
}