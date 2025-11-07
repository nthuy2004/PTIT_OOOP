//package com.ptit.dental.view;
//
//import com.ptit.dental.base.BaseView;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//
//public class InvoiceView extends BaseView {
//
//    // ====== UI Components ======
//    private JTextField txtPatientID, txtAdmissionName, txtAdmissionDate, txtDischargeDate;
//    private JTable invoiceTable;
//    private JTextField txtSubTotal, txtTax, txtDiscount, txtGrandTotal;
//    private JButton btnPrint, btnSavePDF, btnClose, btnHome;
//
//    public InvoiceView() {
//        setTitle("Quản lý răng - Hóa đơn");
//        setSize(950, 550);
//        setLocationRelativeTo(null);
//
//        initComponents();
//        layoutComponents();
//        setVisible(true);
//    }
//
//    private void initComponents() {
//
//        // Form fields
//        txtPatientID = new JTextField(20);
//        txtAdmissionName = new JTextField(20);
//        txtAdmissionDate = new JTextField(20);
//        txtDischargeDate = new JTextField(20);
//
//        // Table
//        String[] columns = {"Service/Item", "Unit Cost", "Quantity", "Total"};
//        Object[][] data = {
//                {"Consultation", "Doctor's Visit", 1, "150.00"},
//                {"Medication", "Antibiotics", 2, "100.00"},
//                {"Room Charge", "Semi-Private", 3, "600.00"}
//        };
//        invoiceTable = new JTable(new DefaultTableModel(data, columns));
//
//        // Summary fields
//        txtSubTotal = new JTextField("850.00", 10);
//        txtTax = new JTextField("85.00", 10);
//        txtDiscount = new JTextField("42.00", 10);
//        txtGrandTotal = new JTextField("892.50", 10);
//
//        // Buttons
//        btnPrint = new JButton("Print Invoice");
//        btnSavePDF = new JButton("Save as PDF");
//        btnClose = new JButton("Close");
//        btnHome = new JButton("Quay về trang chủ");
//        btnHome.setBackground(new Color(76, 175, 80));
//        btnHome.setForeground(Color.WHITE);
//        btnHome.setFocusPainted(false);
//        btnHome.addActionListener(e -> dispose());
//    }
//
//    private void layoutComponents() {
//
//        setLayout(new BorderLayout());
//
//        // ---- Title ----
//        JLabel titleLabel = new JLabel("Patient Invoice");
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        add(titleLabel, BorderLayout.NORTH);
//
//        // ---- Main Panel ----
//        JPanel mainPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(8, 10, 8, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Left Form
//        gbc.gridx = 0; gbc.gridy = 0;
//        mainPanel.add(new JLabel("Patient ID:"), gbc);
//        gbc.gridx = 1;
//        mainPanel.add(txtPatientID, gbc);
//
//        gbc.gridx = 0; gbc.gridy++;
//        mainPanel.add(new JLabel("Admission Name:"), gbc);
//        gbc.gridx = 1;
//        mainPanel.add(txtAdmissionName, gbc);
//
//        gbc.gridx = 0; gbc.gridy++;
//        mainPanel.add(new JLabel("Admission Date:"), gbc);
//        gbc.gridx = 1;
//        mainPanel.add(txtAdmissionDate, gbc);
//
//        gbc.gridx = 0; gbc.gridy++;
//        mainPanel.add(new JLabel("Discharge Date:"), gbc);
//        gbc.gridx = 1;
//        mainPanel.add(txtDischargeDate, gbc);
//
//        // Table Panel
//        JPanel tablePanel = new JPanel(new BorderLayout());
//        tablePanel.setBorder(BorderFactory.createTitledBorder("Invoice Details"));
//        tablePanel.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);
//
//        gbc.gridx = 2; gbc.gridy = 0;
//        gbc.gridheight = 4;
//        gbc.fill = GridBagConstraints.BOTH;
//        mainPanel.add(tablePanel, gbc);
//
//        // Summary Panel
//        JPanel summaryPanel = new JPanel(new GridBagLayout());
//        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
//
//        GridBagConstraints sgbc = new GridBagConstraints();
//        sgbc.insets = new Insets(5, 5, 5, 5);
//        sgbc.fill = GridBagConstraints.HORIZONTAL;
//
//        sgbc.gridx = 0; sgbc.gridy = 0;
//        summaryPanel.add(new JLabel("Sub-Total:"), sgbc);
//        sgbc.gridx = 1;
//        summaryPanel.add(txtSubTotal, sgbc);
//
//        sgbc.gridx = 0; sgbc.gridy++;
//        summaryPanel.add(new JLabel("Tax (10%):"), sgbc);
//        sgbc.gridx = 1;
//        summaryPanel.add(txtTax, sgbc);
//
//        sgbc.gridx = 0; sgbc.gridy++;
//        summaryPanel.add(new JLabel("Discount (5%):"), sgbc);
//        sgbc.gridx = 1;
//        summaryPanel.add(txtDiscount, sgbc);
//
//        sgbc.gridx = 0; sgbc.gridy++;
//        JLabel totalLabel = new JLabel("Grand Total:");
//        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        summaryPanel.add(totalLabel, sgbc);
//        sgbc.gridx = 1;
//        summaryPanel.add(txtGrandTotal, sgbc);
//
//        gbc.gridx = 3; gbc.gridy = 0;
//        gbc.gridheight = 4;
//        mainPanel.add(summaryPanel, gbc);
//
//        add(mainPanel, BorderLayout.CENTER);
//
//        // Buttons Panel
//        JPanel btnPanel = new JPanel();
//        btnPanel.add(btnPrint);
//        btnPanel.add(btnSavePDF);
//        btnPanel.add(btnClose);
//        btnPanel.add(btnHome);
//
//        add(btnPanel, BorderLayout.SOUTH);
//    }
//}

package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InvoiceView extends BaseView {
    private JTextField searchField;
    private JButton searchButton;
    private JTable invoiceTable;
    private JLabel titleLabel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton btnHome;

    public InvoiceView() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        // Title setup
        titleLabel = new JLabel("QUẢN LÝ HÓA ĐƠN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Search components
        searchField = new JTextField(30);
        searchButton = new JButton("Tìm");
        searchButton.setBackground(new Color(255, 87, 34));
        searchButton.setForeground(Color.WHITE);

        // Action buttons
        String imagePath = "/com/ptit/dental/image/";
        addButton = new JButton();
        addButton.setIcon(new ImageIcon(getClass().getResource(imagePath + "plus.png")));
        addButton.setToolTipText("Tạo hóa đơn mới");

        deleteButton = new JButton();
        deleteButton.setIcon(new ImageIcon(getClass().getResource(imagePath + "bin.png")));
        deleteButton.setToolTipText("Xóa hóa đơn");

        editButton = new JButton();
        editButton.setIcon(new ImageIcon(getClass().getResource(imagePath + "compose.png")));
        editButton.setToolTipText("Sửa hóa đơn");

        // Nút quay về trang chủ
        btnHome = new JButton("Quay về trang chủ");
        btnHome.setBackground(new Color(76, 175, 80));
        btnHome.setForeground(Color.WHITE);
        btnHome.setFocusPainted(false);
        btnHome.addActionListener(e -> dispose());

        // Table setup
        String[] columnNames = {"ID", "Ngày tạo", "Dịch vụ", "Tổng tiền"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        invoiceTable = new JTable(model);
        invoiceTable.getTableHeader().setReorderingAllowed(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Top panel with icon and title
        JPanel topPanel = new JPanel(new BorderLayout());
        String imagePath = "/com/ptit/dental/image/";
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath + "invoice.png"));
        JLabel iconLabel = new JLabel(icon);
        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(addButton);
        actionPanel.add(deleteButton);
        actionPanel.add(editButton);
        actionPanel.add(btnHome);

        // Combine header elements
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(topPanel, BorderLayout.NORTH);
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.add(searchPanel, BorderLayout.WEST);
        middlePanel.add(actionPanel, BorderLayout.EAST);
        headerPanel.add(middlePanel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table panel
        JScrollPane tableScrollPane = new JScrollPane(invoiceTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Add components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Frame properties
        setTitle("Quản lý răng - Hóa đơn");
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    // Getters
    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JTable getInvoiceTable() {
        return invoiceTable;
    }

    // Method to add invoice to table
    public void addInvoiceToTable(String id, String createdTime, String service, double total) {
        DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        model.addRow(new Object[]{id, createdTime, service, String.format("%.2f", total)});
    }
}