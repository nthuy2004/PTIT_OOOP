package com.ptit.dental.view;

import javax.swing.*;
import java.awt.*;

import com.ptit.dental.controller.InvoiceController;
import com.toedter.calendar.JDateChooser;

public class CreatingInvoiceView extends JDialog {
    private JTextField txtPatientID;
    private JComboBox<String> serviceComboBox;
    private JTextField txtAmount;
    private JDateChooser dateChooser;
    private JButton saveButton;
    private JButton cancelButton;

    public CreatingInvoiceView(InvoiceController parent) {
        super((Frame) parent.getView(), "Tạo hóa đơn mới", true);
        initComponents();
        setupLayout();
        pack();
        setLocationRelativeTo(parent.getView());
    }

    private void initComponents() {
        txtPatientID = new JTextField(20);
        String[] services = {"Khám răng", "Niềng răng", "Nhổ răng", "Trám răng"};
        serviceComboBox = new JComboBox<>(services);
        txtAmount = new JTextField(20);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");

        saveButton = new JButton("Lưu");
        cancelButton = new JButton("Hủy");

        cancelButton.addActionListener(e -> dispose());
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add components
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã bệnh nhân:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtPatientID, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Dịch vụ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(serviceComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Số tiền:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtAmount, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Ngày tạo:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(dateChooser, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JTextField getTxtPatientID() { return txtPatientID; }
    public JComboBox<String> getServiceComboBox() { return serviceComboBox; }
    public JTextField getTxtAmount() { return txtAmount; }
    public JDateChooser getDateChooser() { return dateChooser; }
    public JButton getSaveButton() { return saveButton; }
}