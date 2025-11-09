//package com.ptit.dental.controller;
//
//import com.ptit.dental.base.BaseController;
//import com.ptit.dental.view.MedicalRecordView;
//
//public class MedicalRecordController extends BaseController<MedicalRecordView> {
//    public MedicalRecordController(MedicalRecordView view) {
//        super(view);
//        initListeners();
//    }
//
//    private void initListeners() {
//        view.saveButton.addActionListener(e -> {
//            // TODO: Implement save logic
//            String patientName=view.namePatientField.getText();
//            String doctor = (String) view.doctorComboBox.getSelectedItem();
//            String diseaseType = (String) view.diseaseTypeComboBox.getSelectedItem();
//            String diagnostic = view.diagnosticArea.getText();
//            String plan = view.planArea.getText();
//            String status = (String) view.statusComboBox.getSelectedItem();
//            java.util.Date date = view.dateChooser.getDate();
//            // Save to database
//        });
//
//        view.cancelButton.addActionListener(e -> {
//            view.dispose();
//        });
//    }
//}

package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.dao.PatientDAO;
import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.model.enums.Gender;
import com.ptit.dental.utils.Database;
import com.ptit.dental.view.PatientFormDialog;
import com.ptit.dental.view.PatientManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PatientManagementController extends BaseController<PatientManagementView> {
    private PatientDAO patientDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PatientManagementController(PatientManagementView view) {
        super(view);
        try {
            Connection conn = Database.getInstance().getConnection();
            patientDAO = new PatientDAO(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối database: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        initController();
        loadPatients();
    }

    private void initController() {
        view.getSearchButton().addActionListener(e -> searchPatients());
        view.getAddButton().addActionListener(e -> addPatient());
        view.getEditButton().addActionListener(e -> editPatient());
        view.getDeleteButton().addActionListener(e -> deletePatient());
    }

    private void loadPatients() {
        try {
            List<Patient> patients = patientDAO.getAll();
            DefaultTableModel model = (DefaultTableModel) view.getPatientTable().getModel();
            model.setRowCount(0);
            
            for (Patient patient : patients) {
                String genderStr = patient.getGender() == Gender.NAM ? "Nam" : 
                                  (patient.getGender() == Gender.NU ? "Nữ" : "Khác");
                model.addRow(new Object[]{
                    patient.getId(),
                    patient.getFullname(),
                    dateFormat.format(patient.getBirthday()),
                    genderStr,
                    patient.getAddress(),
                    patient.getPhone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchPatients() {
        String keyword = view.getSearchField().getText().trim();
        if (keyword.isEmpty()) {
            loadPatients();
            return;
        }
        
        try {
            List<Patient> patients = patientDAO.searchByName(keyword);
            DefaultTableModel model = (DefaultTableModel) view.getPatientTable().getModel();
            model.setRowCount(0);
            
            for (Patient patient : patients) {
                String genderStr = patient.getGender() == Gender.NAM ? "Nam" : 
                                  (patient.getGender() == Gender.NU ? "Nữ" : "Khác");
                model.addRow(new Object[]{
                    patient.getId(),
                    patient.getFullname(),
                    dateFormat.format(patient.getBirthday()),
                    genderStr,
                    patient.getAddress(),
                    patient.getPhone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPatient() {
        PatientFormDialog dialog = new PatientFormDialog(view, null);
        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            Patient patient = dialog.getPatient();
            try {
                patientDAO.insert(patient);
                JOptionPane.showMessageDialog(view, "Thêm bệnh nhân thành công!", 
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadPatients();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Lỗi thêm bệnh nhân: " + e.getMessage(), 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editPatient() {
        int selectedRow = view.getPatientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bệnh nhân để chỉnh sửa!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int patientId = (Integer) view.getPatientTable().getValueAt(selectedRow, 0);
            Patient patient = patientDAO.getById(patientId);
            
            if (patient != null) {
                PatientFormDialog dialog = new PatientFormDialog(view, patient);
                dialog.setVisible(true);
                
                if (dialog.isSaved()) {
                    Patient updatedPatient = dialog.getPatient();
                    if (patientDAO.update(updatedPatient)) {
                        JOptionPane.showMessageDialog(view, "Cập nhật thông tin bệnh nhân thành công!", 
                                "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadPatients();
                    } else {
                        JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", 
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        int selectedRow = view.getPatientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bệnh nhân để xóa!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int patientId = (Integer) view.getPatientTable().getValueAt(selectedRow, 0);
            String name = view.getPatientTable().getValueAt(selectedRow, 1).toString();
            
            int confirm = JOptionPane.showConfirmDialog(view,
                    "Bạn có chắc chắn muốn xóa bệnh nhân " + name + "?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (patientDAO.delete(patientId)) {
                    JOptionPane.showMessageDialog(view, "Đã xóa bệnh nhân thành công!", 
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadPatients();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi xóa bệnh nhân: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void show() {
        view.setVisible(true);
    }
}
