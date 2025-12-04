/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class PatientDetailDialog extends BaseView {

    public JTextField txtFullname, txtBirthday, txtGender, txtPhone;
    public JTextArea txtAddress;

    public JTable tblRecords, tblAppointments;

    public JButton btnAddRecord, btnViewRecord, btnEditRecord;
    public JButton btnAddAppointment, btnViewAppointment, btnEditAppointment, btnDeleteAppointment;

    public PatientDetailDialog() {
        setTitle("Hồ sơ bệnh án của bệnh nhân");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        initLayout();
    }

    private void initLayout() {
        add(createPatientInfoPanel(), BorderLayout.NORTH);
        add(createTabbedPane(), BorderLayout.CENTER);
    }

    private JPanel createPatientInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel lblFullname = new JLabel("Tên đầy đủ:");
        JLabel lblBirthday = new JLabel("Ngày sinh:");
        JLabel lblGender = new JLabel("Giới tính:");
        JLabel lblPhone = new JLabel("SĐT:");
        JLabel lblAddress = new JLabel("Địa chỉ:");

        txtFullname = new JTextField(20);
        txtBirthday = new JTextField(10);
        txtGender = new JTextField(8);
        txtPhone = new JTextField(15);
        txtAddress = new JTextArea(3, 30);

        txtFullname.setEditable(false);
        txtBirthday.setEditable(false);
        txtGender.setEditable(false);
        txtPhone.setEditable(false);
        txtAddress.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(lblFullname, c);

        c.gridx = 1;
        panel.add(txtFullname, c);

        c.gridx = 2;
        panel.add(lblBirthday, c);

        c.gridx = 3;
        panel.add(txtBirthday, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(lblGender, c);

        c.gridx = 1;
        panel.add(txtGender, c);

        c.gridx = 2;
        panel.add(lblPhone, c);

        c.gridx = 3;
        panel.add(txtPhone, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(lblAddress, c);

        c.gridx = 1;
        c.gridwidth = 3;
        panel.add(new JScrollPane(txtAddress), c);

        return panel;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Danh sách bệnh án", createMedicalRecordsPanel());
        tabs.add("Lịch hẹn", createAppointmentsPanel());

        return tabs;
    }

    private JPanel createMedicalRecordsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = { "ID", "Bác sĩ điều trị", "Dịch vụ đã dùng", "Thời gian tạo", "Chẩn đoán",
                "Kế hoạch điều trị", "Trạng thái" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table read-only
            }
        };
        tblRecords = new JTable(model);
        tblRecords.getTableHeader().setReorderingAllowed(false);
        tblRecords.setRowHeight(25);

        panel.add(new JScrollPane(tblRecords), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnAddRecord = new JButton("Thêm");
        btnViewRecord = new JButton("Chi tiết");
        btnEditRecord = new JButton("Sửa");

        buttonPanel.add(btnAddRecord);
        buttonPanel.add(btnViewRecord);
        buttonPanel.add(btnEditRecord);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {
                "Mã lịch hẹn",
                "Tên bệnh nhân",
                "Ngày",
                "Giờ",
                "Ghi chú",
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table read-only
            }
        };

        tblAppointments = new JTable(model);
        tblAppointments.getTableHeader().setReorderingAllowed(false);
        tblAppointments.setRowHeight(25);

        panel.add(new JScrollPane(tblAppointments), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnAddAppointment = new JButton("Thêm");
        btnViewAppointment = new JButton("Xem");
        btnEditAppointment = new JButton("Sửa");
        btnDeleteAppointment = new JButton("Xoá");

        buttonPanel.add(btnAddAppointment);
        buttonPanel.add(btnViewAppointment);
        buttonPanel.add(btnEditAppointment);
        buttonPanel.add(btnDeleteAppointment);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
}
