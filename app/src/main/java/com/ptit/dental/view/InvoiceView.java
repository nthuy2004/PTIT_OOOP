package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InvoiceView extends BaseView {

    // ====== UI Components ======
    private JTextField txtPatientID, txtAdmissionName, txtAdmissionDate, txtDischargeDate;
    private JTable invoiceTable;
    private JTextField txtSubTotal, txtTax, txtDiscount, txtGrandTotal;
    private JButton btnPrint, btnSavePDF, btnClose, btnHome;

    public InvoiceView() {
        setTitle("Quản lý răng - Hóa đơn");
        setSize(950, 550);
        setLocationRelativeTo(null);

        initComponents();
        layoutComponents();
        setVisible(true);
    }

    private void initComponents() {

        // Form fields
        txtPatientID = new JTextField(20);
        txtAdmissionName = new JTextField(20);
        txtAdmissionDate = new JTextField(20);
        txtDischargeDate = new JTextField(20);

        // Table
        String[] columns = {"Service/Item", "Unit Cost", "Quantity", "Total"};
        Object[][] data = {
                {"Consultation", "Doctor's Visit", 1, "150.00"},
                {"Medication", "Antibiotics", 2, "100.00"},
                {"Room Charge", "Semi-Private", 3, "600.00"}
        };
        invoiceTable = new JTable(new DefaultTableModel(data, columns));

        // Summary fields
        txtSubTotal = new JTextField("850.00", 10);
        txtTax = new JTextField("85.00", 10);
        txtDiscount = new JTextField("42.00", 10);
        txtGrandTotal = new JTextField("892.50", 10);

        // Buttons
        btnPrint = new JButton("Print Invoice");
        btnSavePDF = new JButton("Save as PDF");
        btnClose = new JButton("Close");
        btnHome = new JButton("Quay về trang chủ");
        btnHome.setBackground(new Color(76, 175, 80));
        btnHome.setForeground(Color.WHITE);
        btnHome.setFocusPainted(false);
        btnHome.addActionListener(e -> dispose());
    }

    private void layoutComponents() {

        setLayout(new BorderLayout());

        // ---- Title ----
        JLabel titleLabel = new JLabel("Patient Invoice");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ---- Main Panel ----
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Left Form
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtPatientID, gbc);

        gbc.gridx = 0; gbc.gridy++;
        mainPanel.add(new JLabel("Admission Name:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtAdmissionName, gbc);

        gbc.gridx = 0; gbc.gridy++;
        mainPanel.add(new JLabel("Admission Date:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtAdmissionDate, gbc);

        gbc.gridx = 0; gbc.gridy++;
        mainPanel.add(new JLabel("Discharge Date:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(txtDischargeDate, gbc);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Invoice Details"));
        tablePanel.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);

        gbc.gridx = 2; gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tablePanel, gbc);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));

        GridBagConstraints sgbc = new GridBagConstraints();
        sgbc.insets = new Insets(5, 5, 5, 5);
        sgbc.fill = GridBagConstraints.HORIZONTAL;

        sgbc.gridx = 0; sgbc.gridy = 0;
        summaryPanel.add(new JLabel("Sub-Total:"), sgbc);
        sgbc.gridx = 1;
        summaryPanel.add(txtSubTotal, sgbc);

        sgbc.gridx = 0; sgbc.gridy++;
        summaryPanel.add(new JLabel("Tax (10%):"), sgbc);
        sgbc.gridx = 1;
        summaryPanel.add(txtTax, sgbc);

        sgbc.gridx = 0; sgbc.gridy++;
        summaryPanel.add(new JLabel("Discount (5%):"), sgbc);
        sgbc.gridx = 1;
        summaryPanel.add(txtDiscount, sgbc);

        sgbc.gridx = 0; sgbc.gridy++;
        JLabel totalLabel = new JLabel("Grand Total:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(totalLabel, sgbc);
        sgbc.gridx = 1;
        summaryPanel.add(txtGrandTotal, sgbc);

        gbc.gridx = 3; gbc.gridy = 0;
        gbc.gridheight = 4;
        mainPanel.add(summaryPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnPrint);
        btnPanel.add(btnSavePDF);
        btnPanel.add(btnClose);
        btnPanel.add(btnHome);

        add(btnPanel, BorderLayout.SOUTH);
    }
}
