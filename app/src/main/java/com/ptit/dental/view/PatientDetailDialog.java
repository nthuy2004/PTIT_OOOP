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
 * Patient Medical Record Detail Dialog
 */
public class PatientDetailDialog extends BaseView {

    // Patient info
    public JTextField txtFullname, txtGender, txtPhone;
    public JTextArea txtAddress;

    // Doctor & Diagnostic info
    public JComboBox<String> cbDoctorName;
    public JComboBox<String> cbServiceType;
    public JTextField txtVisitDate;
    public JComboBox<String> cbTreatmentStatus;
    public JTextArea txtDiagnosis, txtTreatmentPlan;

    // Services table
    public JTable tblServices;

    // Buttons
    public JButton btnSave, btnCancel;

    public PatientDetailDialog() {
        setTitle("Chi tiết hồ sơ bệnh án");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    // ---------------------------- HEADER PANEL ----------------------------
    private JPanel createHeaderPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 12, 10, 12);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Chi tiết hồ sơ bệnh án");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        p.add(lblTitle, c);

        // Reset for patient info
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        // Row 1 - Patient name & Doctor
        c.gridx = 0;
        c.gridy = 1;
        p.add(new JLabel("Tên bệnh nhân:"), c);
        txtFullname = new JTextField(15);
        txtFullname.setEditable(false);
        c.gridx = 1;
        p.add(txtFullname, c);

        c.gridx = 2;
        p.add(new JLabel("Bác sĩ điều trị:"), c);
        cbDoctorName = new JComboBox<>();
        c.gridx = 3;
        p.add(cbDoctorName, c);

        // Row 2 - Service Type & Visit Date
        c.gridx = 0;
        c.gridy = 2;
        p.add(new JLabel("Loại bệnh/dịch vụ chính:"), c);
        cbServiceType = new JComboBox<>();
        c.gridx = 1;
        p.add(cbServiceType, c);

        c.gridx = 2;
        p.add(new JLabel("Ngày khám:"), c);
        txtVisitDate = new JTextField(15);
        c.gridx = 3;
        p.add(txtVisitDate, c);

        // Row 3 - Gender & Status
        c.gridx = 0;
        c.gridy = 3;
        p.add(new JLabel("Giới tính:"), c);
        txtGender = new JTextField(15);
        txtGender.setEditable(false);
        c.gridx = 1;
        p.add(txtGender, c);

        c.gridx = 2;
        p.add(new JLabel("Trạng thái:"), c);
        cbTreatmentStatus = new JComboBox<>(new String[] { "Not Yet", "Processing", "Completed" });
        c.gridx = 3;
        p.add(cbTreatmentStatus, c);

        // Row 4 - Phone
        c.gridx = 0;
        c.gridy = 4;
        p.add(new JLabel("Số điện thoại:"), c);
        txtPhone = new JTextField(15);
        txtPhone.setEditable(false);
        c.gridx = 1;
        p.add(txtPhone, c);

        return p;
    }

    // ---------------------------- CONTENT PANEL (Main Area)
    // ----------------------------
    private JPanel createContentPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 12, 10, 12);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

        // Section 1: Address
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        p.add(new JLabel("Địa chỉ:"), c);

        txtAddress = new JTextArea(2, 50);
        txtAddress.setEditable(false);
        c.gridy = 1;
        p.add(new JScrollPane(txtAddress), c);

        // Section 2: Services used
        c.gridy = 2;
        p.add(new JLabel("Các dịch vụ đã sử dụng trong hồ sơ bệnh án:"), c);

        String[] cols = { "Tên dịch vụ", "Số lượng", "Ghi chú", "Đơn giá" };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblServices = new JTable(model);
        tblServices.setRowHeight(24);
        tblServices.getTableHeader().setReorderingAllowed(false);
        c.gridy = 3;
        c.weighty = 0.4;
        p.add(new JScrollPane(tblServices), c);

        // Section 3: Diagnosis
        c.gridy = 4;
        c.weighty = 0;
        p.add(new JLabel("Chẩn đoán:"), c);

        txtDiagnosis = new JTextArea(3, 50);
        c.gridy = 5;
        c.weighty = 0.3;
        p.add(new JScrollPane(txtDiagnosis), c);

        // Section 4: Treatment Plan
        c.gridy = 6;
        c.weighty = 0;
        p.add(new JLabel("Kế hoạch điều trị:"), c);

        txtTreatmentPlan = new JTextArea(3, 50);
        c.gridy = 7;
        c.weighty = 0.3;
        p.add(new JScrollPane(txtTreatmentPlan), c);

        // Buttons
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0;
        p.add(btnPanel, c);

        return p;
    }
}
