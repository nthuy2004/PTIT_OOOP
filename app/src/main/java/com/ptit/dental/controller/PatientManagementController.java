package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.dao.PatientDAO;
import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.model.enums.Gender;
import com.ptit.dental.utils.Database;
import com.ptit.dental.view.PatientDetailDialog;
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
    private List<Patient> patients;

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
        
        view.getPatientTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && view.getPatientTable().getSelectedRow() != -1) { // double click
                    int selectedRow = view.getPatientTable().getSelectedRow();
                    Patient p = patients.get(selectedRow);
                    if(p != null)
                    {
                        PatientDetailDialog dlg = new PatientDetailDialog();
                        new PatientDetailController(dlg, p).show();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(view, "Wrong selection", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void renderPatients(List<Patient> patients)
    {
        DefaultTableModel model = (DefaultTableModel) view.getPatientTable().getModel();
        model.setRowCount(0);

        for (Patient patient : patients) {
            model.addRow(new Object[]{
                patient.getId(),
                patient.getFullname(),
                dateFormat.format(patient.getBirthday()),
                patient.getGender().toString(),
                patient.getAddress(),
                patient.getPhone()
            });
        }
    }
    
    private void loadPatients() {
        try {
            patients = patientDAO.getAll();
            renderPatients(patients);
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
            patients = patientDAO.searchByName(keyword);
            renderPatients(patients);
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
