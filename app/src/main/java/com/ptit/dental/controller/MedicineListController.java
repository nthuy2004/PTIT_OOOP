package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.dao.MedicineDAO;
import com.ptit.dental.model.entity.Medicine;
import com.ptit.dental.utils.Database;
import com.ptit.dental.view.MedicineFormDialog;
import com.ptit.dental.view.MedicineListView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MedicineListController extends BaseController<MedicineListView> {
    private MedicineDAO medicineDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public MedicineListController(MedicineListView view) {
        super(view);
        try {
            Connection conn = Database.getInstance().getConnection();
            medicineDAO = new MedicineDAO(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối database: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        initListeners();
        loadMedicines();
    }

    private void initListeners() {
        view.getSearchButton().addActionListener(e -> searchMedicine());
        view.getAddButton().addActionListener(e -> addMedicine());
        view.getDeleteButton().addActionListener(e -> deleteMedicine());
        view.getEditButton().addActionListener(e -> editMedicine());
    }

    private void loadMedicines() {
        try {
            List<Medicine> medicines = medicineDAO.getAll();
            DefaultTableModel model = (DefaultTableModel) view.getMedicineTable().getModel();
            model.setRowCount(0);
            
            for (Medicine medicine : medicines) {
                model.addRow(new Object[]{
                    medicine.getId(),
                    medicine.getName(),
                    dateFormat.format(medicine.getImportDate()),
                    dateFormat.format(medicine.getExpiryDate()),
                    String.format("%.2f", medicine.getPrice()),
                    medicine.getQuantity()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchMedicine() {
        String keyword = view.getSearchField().getText().trim();
        if (keyword.isEmpty()) {
            loadMedicines();
            return;
        }
        
        try {
            List<Medicine> medicines = medicineDAO.searchByName(keyword);
            DefaultTableModel model = (DefaultTableModel) view.getMedicineTable().getModel();
            model.setRowCount(0);
            
            for (Medicine medicine : medicines) {
                model.addRow(new Object[]{
                    medicine.getId(),
                    medicine.getName(),
                    dateFormat.format(medicine.getImportDate()),
                    dateFormat.format(medicine.getExpiryDate()),
                    String.format("%.2f", medicine.getPrice()),
                    medicine.getQuantity()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMedicine() {
        MedicineFormDialog dialog = new MedicineFormDialog(view, null);
        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            Medicine medicine = dialog.getMedicine();
            try {
                medicineDAO.insert(medicine);
                JOptionPane.showMessageDialog(view, "Thêm thuốc thành công!", 
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadMedicines();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Lỗi thêm thuốc: " + e.getMessage(), 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteMedicine() {
        int selectedRow = view.getMedicineTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn thuốc để xóa!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int medicineId = (Integer) view.getMedicineTable().getValueAt(selectedRow, 0);
            String name = view.getMedicineTable().getValueAt(selectedRow, 1).toString();
            
            int confirm = JOptionPane.showConfirmDialog(view,
                    "Bạn có chắc chắn muốn xóa thuốc " + name + "?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (medicineDAO.delete(medicineId)) {
                    JOptionPane.showMessageDialog(view, "Đã xóa thuốc thành công!", 
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadMedicines();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi xóa thuốc: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editMedicine() {
        int selectedRow = view.getMedicineTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn thuốc để chỉnh sửa!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int medicineId = (Integer) view.getMedicineTable().getValueAt(selectedRow, 0);
            Medicine medicine = medicineDAO.getById(medicineId);
            
            if (medicine != null) {
                MedicineFormDialog dialog = new MedicineFormDialog(view, medicine);
                dialog.setVisible(true);
                
                if (dialog.isSaved()) {
                    Medicine updatedMedicine = dialog.getMedicine();
                    if (medicineDAO.update(updatedMedicine)) {
                        JOptionPane.showMessageDialog(view, "Cập nhật thông tin thuốc thành công!", 
                                "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadMedicines();
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
}