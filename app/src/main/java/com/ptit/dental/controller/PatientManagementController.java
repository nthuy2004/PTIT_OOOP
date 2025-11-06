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
import com.ptit.dental.view.PatientManagementView;
import com.ptit.dental.view.PatientManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientManagementController extends BaseController<PatientManagementView> {

    public PatientManagementController(PatientManagementView view) {
        super(view);
        initController();
    }

    private void initController() {
        view.getSearchButton().addActionListener(e -> searchPatients());
        view.getAddButton().addActionListener(e -> addPatient());
        view.getEditButton().addActionListener(e -> editPatient());
        view.getDeleteButton().addActionListener(e -> deletePatient());
        //view.getRecordButton().addActionListener(e -> openMedicalRecord());
    }

    private void searchPatients() {
        String keyword = view.getSearchField().getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập từ khóa để tìm kiếm!");
            return;
        }
        JOptionPane.showMessageDialog(view, "Tìm bệnh nhân theo từ khóa: " + keyword);
        // TODO: thêm logic tìm kiếm thực tế từ database
    }

    private void addPatient() {
        JOptionPane.showMessageDialog(view, "Thêm bệnh nhân mới!");
        // TODO: mở form thêm bệnh nhân (AddPatientView)
    }

    private void editPatient() {
        int selectedRow = view.getPatientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bệnh nhân để chỉnh sửa!");
            return;
        }

        String name = view.getPatientTable().getValueAt(selectedRow, 1).toString();
        JOptionPane.showMessageDialog(view, "Chỉnh sửa thông tin của: " + name);
        // TODO: mở form EditPatientView
    }

    private void deletePatient() {
        int selectedRow = view.getPatientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bệnh nhân để xóa!");
            return;
        }

        String name = view.getPatientTable().getValueAt(selectedRow, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(view,
                "Bạn có chắc chắn muốn xóa bệnh nhân " + name + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) view.getPatientTable().getModel();
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(view, "Đã xóa bệnh nhân: " + name);
        }
    }

    private void openMedicalRecord() {
        int selectedRow = view.getPatientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bệnh nhân để xem hồ sơ!");
            return;
        }

        String name = view.getPatientTable().getValueAt(selectedRow, 1).toString();
        JOptionPane.showMessageDialog(view, "Mở hồ sơ bệnh án của: " + name);

        // Mở cửa sổ hồ sơ bệnh án (MedicalRecordView)

    }

    @Override
    public void show() {
        view.setVisible(true);
    }
}
