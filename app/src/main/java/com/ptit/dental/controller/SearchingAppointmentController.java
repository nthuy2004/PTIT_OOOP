package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.dao.AppointmentDAO;
import com.ptit.dental.model.entity.Appointment;
import com.ptit.dental.utils.Database;
import com.ptit.dental.utils.Util;
import com.ptit.dental.view.AppointmentFormDialog;
import com.ptit.dental.view.SearchingAppointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class SearchingAppointmentController extends BaseController<SearchingAppointment> {

    private AppointmentDAO appointmentDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public SearchingAppointmentController(SearchingAppointment view) {
        super(view);

        try {
            Connection conn = Database.getInstance().getConnection();
            appointmentDAO = new AppointmentDAO(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi kết nối Database: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        initController();
        loadAppointments();
    }

    private void initController() {
        view.getSearchButton().addActionListener(e -> searchAppointments());
        view.getBtnAddAppointment().addActionListener(e -> addAppointment());
        view.getBtnEditAppointment().addActionListener(e -> editAppointment());
        view.getBtnDeleteAppointment().addActionListener(e -> deleteAppointment());
    }

    private void loadAppointments() {
        try {
            List<Appointment> list = appointmentDAO.getAll();
            DefaultTableModel model = (DefaultTableModel) view.getAppointmentTable().getModel();
            model.setRowCount(0);

            for (Appointment a : list) {
                model.addRow(new Object[] {
                        a.getId(),
                        a.getPatient().getFullname(),
                        Util.formatDate(a.getDate()),
                        a.getTimeOfDatetime(),
                        a.getReason(),
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi tải danh sách lịch hẹn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * ===============================
     * 📌 SEARCH
     * ===============================
     */
    private void searchAppointments() {
        String keyword = view.getSearchField().getText().trim();

        if (keyword.isEmpty()) {
            loadAppointments();
            return;
        }

        try {
            List<Appointment> list = appointmentDAO.searchByPatientName(keyword);
            DefaultTableModel model = (DefaultTableModel) view.getAppointmentTable().getModel();
            model.setRowCount(0);

            for (Appointment a : list) {
                model.addRow(new Object[] {
                        a.getId(),
                        a.getPatient().getFullname(),
                        Util.formatDate(a.getDate()),
                        a.getTimeOfDatetime(),
                        a.getReason(),
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi tìm kiếm lịch hẹn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * ===============================
     * 📌 ADD APPOINTMENT
     * ===============================
     */
    private void addAppointment() {
        AppointmentFormDialog dialog = new AppointmentFormDialog(view, null);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Appointment newApp = dialog.getAppointment();

            try {
                appointmentDAO.insert(newApp);
                JOptionPane.showMessageDialog(view,
                        "Thêm lịch hẹn thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadAppointments();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view,
                        "Lỗi thêm lịch hẹn: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * ===============================
     * 📌 EDIT APPOINTMENT
     * ===============================
     */
    private void editAppointment() {
        int row = view.getAppointmentTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng chọn lịch hẹn để sửa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = (Integer) view.getAppointmentTable().getValueAt(row, 0);
            Appointment app = appointmentDAO.getById(id);

            AppointmentFormDialog dialog = new AppointmentFormDialog(view, app);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                Appointment updated = dialog.getAppointment();
                appointmentDAO.update(updated);

                JOptionPane.showMessageDialog(view,
                        "Cập nhật lịch hẹn thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadAppointments();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * ===============================
     * 📌 DELETE APPOINTMENT
     * ===============================
     */
    private void deleteAppointment() {
        int row = view.getAppointmentTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng chọn lịch hẹn để xóa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) view.getAppointmentTable().getValueAt(row, 0);
        String patientName = view.getAppointmentTable().getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(view,
                "Bạn có chắc chắn muốn xóa lịch hẹn của bệnh nhân " + patientName + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                appointmentDAO.delete(id);
                JOptionPane.showMessageDialog(view,
                        "Xóa lịch hẹn thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadAppointments();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view,
                        "Lỗi xóa lịch hẹn: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void show() {
        view.setVisible(true);
    }
}
