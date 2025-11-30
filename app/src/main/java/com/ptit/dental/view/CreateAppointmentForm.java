package com.ptit.dental.view;

import javax.swing.*;
import java.awt.*;

public class CreateAppointmentForm extends JDialog {

    private JTextField txtPatientName, txtDate, txtTime;
    private JComboBox<String> cbService;
    private JTextArea txtNote;
    private JButton btnSave, btnCancel;

    public CreateAppointmentForm(JFrame parent) {
        super(parent, "Tạo lịch hẹn mới", true);
        initComponents();
        setupLayout();
        setSize(400, 400);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        txtPatientName = new JTextField(20);
        txtDate = new JTextField(10);
        txtTime = new JTextField(10);

        cbService = new JComboBox<>(new String[]{
                "Khám tổng quát", "Nhổ răng", "Trám răng", "Tẩy trắng", "Niềng răng"
        });

        txtNote = new JTextArea(4, 20);

        btnSave = new JButton("Lưu");
        btnSave.setBackground(new Color(76, 175, 80));
        btnSave.setForeground(Color.WHITE);

        btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dispose());
    }

    private void setupLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Tên bệnh nhân:"), gbc);
        gbc.gridx = 1; panel.add(txtPatientName, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Ngày (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; panel.add(txtDate, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Giờ (HH:mm):"), gbc);
        gbc.gridx = 1; panel.add(txtTime, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Dịch vụ:"), gbc);
        gbc.gridx = 1; panel.add(cbService, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Ghi chú:"), gbc);
        gbc.gridx = 1; panel.add(new JScrollPane(txtNote), gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        panel.add(btnSave, gbc);

        gbc.gridx = 1;
        panel.add(btnCancel, gbc);

        add(panel);
    }

    public JButton getBtnSave() { return btnSave; }
    public String getPatientName() { return txtPatientName.getText(); }
    public String getDate() { return txtDate.getText(); }
    public String getTime() { return txtTime.getText(); }
    public String getService() { return cbService.getSelectedItem().toString(); }
    public String getNote() { return txtNote.getText(); }
}
