package com.ptit.dental.view;

import com.ptit.dental.model.dao.AppointmentDAO;
import com.ptit.dental.model.dao.MedicalRecordDAO;
import com.ptit.dental.model.entity.Appointment;
import com.ptit.dental.model.entity.MedicalRecord;
import com.ptit.dental.utils.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class PatientDetailDialog extends JDialog {
    private int patientId;

    public PatientDetailDialog(JFrame parent, int patientId) {
        super(parent, "Chi tiết bệnh nhân", true);
        this.patientId = patientId;
        setSize(900, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        try {
            Connection conn = Database.getInstance().getConnection();
            AppointmentDAO appointmentDAO = new AppointmentDAO(conn);
            MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO(conn);
            List<Appointment> appointments = appointmentDAO.getByPatientId(patientId);
            List<MedicalRecord> records = medicalRecordDAO.getByPatientId(patientId);

            // Lịch hẹn table
            String[] apptHeaders = {"ID", "Thời gian", "Ghi chú/Lý do"};
            DefaultTableModel apptModel = new DefaultTableModel(apptHeaders, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; } };
            for (Appointment appt : appointments) {
                apptModel.addRow(new Object[]{appt.getId(), appt.time, appt.getService()});
            }

            JTable tblAppointments = new JTable(apptModel);
            JScrollPane spAppointments = new JScrollPane(tblAppointments);

            // Hồ sơ bệnh table
            String[] recordHeaders = {"ID", "Chẩn đoán", "Kế hoạch", "Trạng thái", "Thời gian"};
            DefaultTableModel recordModel = new DefaultTableModel(recordHeaders, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; } };
            for (MedicalRecord rec : records) {
                recordModel.addRow(new Object[]{rec.getId(), rec.getDiagnostic(), rec.getPlan(), rec.getStatus(), rec.getTime()});
            }

            JTable tblRecords = new JTable(recordModel);
            JScrollPane spRecords = new JScrollPane(tblRecords);

            // Tabs
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Lịch hẹn", spAppointments);
            tabbedPane.addTab("Hồ sơ bệnh", spRecords);

            add(tabbedPane, BorderLayout.CENTER);
            JButton btnClose = new JButton("Đóng");
            btnClose.addActionListener(e -> dispose());
            JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelBtn.add(btnClose);
            add(panelBtn, BorderLayout.SOUTH);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu chi tiết bệnh nhân: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
