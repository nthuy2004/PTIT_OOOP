//package com.ptit.dental.view;
//
//import com.ptit.dental.model.dao.PatientDAO;
//import com.ptit.dental.model.entity.Invoice;
//import com.ptit.dental.model.entity.InvoiceItem;
//import com.ptit.dental.model.entity.Patient;
//import com.ptit.dental.utils.Database;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class InvoiceFormDialog extends JDialog {
//    private JComboBox<String> patientComboBox;
//    private JTable itemsTable;
//    private JTextField txtServiceName;
//    private JTextField txtUnitPrice;
//    private JTextField txtQuantity;
//    private JButton btnAddItem;
//    private JButton btnRemoveItem;
//    private JTextField txtSubTotal;
//    private JTextField txtTax;
//    private JTextField txtDiscount;
//    private JTextField txtTotal;
//    private JTextArea txtNote;
//    private JButton btnSave;
//    private JButton btnCancel;
//
//    private Invoice invoice;
//    private boolean saved = false;
//    private List<Patient> patients;
//    private DefaultTableModel itemsTableModel;
//    private double taxRate = 0.10; // 10%
//    private double discountRate = 0.05; // 5%
//
//    public InvoiceFormDialog(JFrame parent, Invoice invoice) {
//        super(parent, invoice == null ? "Tạo hóa đơn mới" : "Chỉnh sửa hóa đơn", true);
//        this.invoice = invoice;
//        loadPatients();
//        initComponents();
//        setupLayout();
//
//        if (invoice != null) {
//            loadInvoiceData();
//        }
//
//        setLocationRelativeTo(parent);
//    }
//
//    private void loadPatients() {
//        try {
//            Connection conn = Database.getInstance().getConnection();
//            PatientDAO patientDAO = new PatientDAO(conn);
//            patients = patientDAO.getAll();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách bệnh nhân: " + e.getMessage(),
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//            patients = new ArrayList<>();
//        }
//    }
//
//    private void initComponents() {
//        // Patient combo box
//        String[] patientNames = new String[patients.size()];
//        for (int i = 0; i < patients.size(); i++) {
//            patientNames[i] = patients.get(i).getId() + " - " + patients.get(i).getFullname();
//        }
//        patientComboBox = new JComboBox<>(patientNames);
//
//        // Items table
//        String[] columnNames = { "Dịch vụ", "Đơn giá", "Số lượng", "Thành tiền" };
//        itemsTableModel = new DefaultTableModel(columnNames, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        itemsTable = new JTable(itemsTableModel);
//        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//        // Item input fields
//        txtServiceName = new JTextField(15);
//        txtUnitPrice = new JTextField(10);
//        txtQuantity = new JTextField(10);
//
//        btnAddItem = new JButton("Thêm");
//        btnAddItem.addActionListener(e -> addItem());
//        btnRemoveItem = new JButton("Xóa");
//        btnRemoveItem.addActionListener(e -> removeItem());
//
//        // Summary fields
//        txtSubTotal = new JTextField(15);
//        txtSubTotal.setEditable(false);
//        txtTax = new JTextField(15);
//        txtTax.setEditable(false);
//        txtDiscount = new JTextField(15);
//        txtDiscount.setEditable(false);
//        txtTotal = new JTextField(15);
//        txtTotal.setEditable(false);
//
//        txtNote = new JTextArea(3, 30);
//        txtNote.setLineWrap(true);
//
//        btnSave = new JButton("Lưu");
//        btnSave.setBackground(new Color(76, 175, 80));
//        btnSave.setForeground(Color.WHITE);
//        btnSave.addActionListener(e -> {
//            if (validateInput()) {
//                saved = true;
//                dispose();
//            }
//        });
//
//        btnCancel = new JButton("Hủy");
//        btnCancel.addActionListener(e -> dispose());
//
//        // Auto calculate is done when items change
//    }
//
//    private void setupLayout() {
//        setLayout(new BorderLayout());
//        setSize(900, 650);
//
//        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        // Top panel - Patient selection (Improved)
//        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
//        topPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createTitledBorder("Thông tin bệnh nhân"),
//                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
//        JLabel patientLabel = new JLabel("Bệnh nhân:");
//        patientLabel.setFont(new Font("Arial", Font.BOLD, 12));
//        topPanel.add(patientLabel);
//        patientComboBox.setPreferredSize(new Dimension(300, 30));
//        topPanel.add(patientComboBox);
//        mainPanel.add(topPanel, BorderLayout.NORTH);
//
//        // Center panel - Items table and input
//        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
//
//        // Items table
//        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
//        tablePanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createTitledBorder("Danh sách dịch vụ"),
//                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//        itemsTable.setRowHeight(25);
//        itemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
//        itemsTable.setFont(new Font("Arial", Font.PLAIN, 12));
//        JScrollPane tableScroll = new JScrollPane(itemsTable);
//        tableScroll.setPreferredSize(new Dimension(0, 200));
//        tablePanel.add(tableScroll, BorderLayout.CENTER);
//        centerPanel.add(tablePanel, BorderLayout.CENTER);
//
//        // Item input panel - Improved layout
//        JPanel itemInputPanel = new JPanel(new BorderLayout(10, 10));
//        itemInputPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createTitledBorder("Thêm dịch vụ"),
//                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//        itemInputPanel.setBackground(new Color(0xC0C0C0));
//
//        // Input fields panel with GridBagLayout for better alignment
//        JPanel inputFieldsPanel = new JPanel(new GridBagLayout());
//        inputFieldsPanel.setOpaque(false);
//        GridBagConstraints gbcInput = new GridBagConstraints();
//        gbcInput.insets = new Insets(5, 5, 5, 5);
//        gbcInput.anchor = GridBagConstraints.WEST;
//
//        // Row 1: Service Name
//        gbcInput.gridx = 0;
//        gbcInput.gridy = 0;
//        gbcInput.weightx = 0;
//        inputFieldsPanel.add(new JLabel("Tên dịch vụ:"), gbcInput);
//        gbcInput.gridx = 1;
//        gbcInput.weightx = 1.0;
//        gbcInput.fill = GridBagConstraints.HORIZONTAL;
//        txtServiceName.setPreferredSize(new Dimension(200, 25));
//        inputFieldsPanel.add(txtServiceName, gbcInput);
//
//        // Row 2: Unit Price and Quantity
//        gbcInput.gridx = 0;
//        gbcInput.gridy = 1;
//        gbcInput.weightx = 0;
//        gbcInput.fill = GridBagConstraints.NONE;
//        inputFieldsPanel.add(new JLabel("Đơn giá:"), gbcInput);
//        gbcInput.gridx = 1;
//        gbcInput.weightx = 0.5;
//        gbcInput.fill = GridBagConstraints.HORIZONTAL;
//        txtUnitPrice.setPreferredSize(new Dimension(120, 25));
//        inputFieldsPanel.add(txtUnitPrice, gbcInput);
//
//        gbcInput.gridx = 2;
//        gbcInput.insets = new Insets(5, 15, 5, 5);
//        inputFieldsPanel.add(new JLabel("Số lượng:"), gbcInput);
//        gbcInput.gridx = 3;
//        gbcInput.weightx = 0.5;
//        gbcInput.insets = new Insets(5, 5, 5, 5);
//        txtQuantity.setPreferredSize(new Dimension(100, 25));
//        inputFieldsPanel.add(txtQuantity, gbcInput);
//
//        itemInputPanel.add(inputFieldsPanel, BorderLayout.CENTER);
//
//        // Buttons panel
//        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
//        buttonsPanel.setOpaque(false);
//        btnAddItem.setPreferredSize(new Dimension(100, 30));
//        btnAddItem.setBackground(new Color(33, 150, 243));
//        btnAddItem.setForeground(Color.WHITE);
//        btnAddItem.setFocusPainted(false);
//        btnRemoveItem.setPreferredSize(new Dimension(100, 30));
//        btnRemoveItem.setBackground(new Color(244, 67, 54));
//        btnRemoveItem.setForeground(Color.WHITE);
//        btnRemoveItem.setFocusPainted(false);
//        buttonsPanel.add(btnAddItem);
//        buttonsPanel.add(btnRemoveItem);
//        itemInputPanel.add(buttonsPanel, BorderLayout.SOUTH);
//
//        centerPanel.add(itemInputPanel, BorderLayout.SOUTH);
//
//        mainPanel.add(centerPanel, BorderLayout.CENTER);
//
//        // Right panel - Summary and Note
//        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
//
//        // Summary panel
//        JPanel summaryPanel = new JPanel(new GridBagLayout());
//        summaryPanel.setBorder(BorderFactory.createTitledBorder("Tổng kết"));
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        summaryPanel.add(new JLabel("Tổng tiền:"), gbc);
//        gbc.gridx = 1;
//        summaryPanel.add(txtSubTotal, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        summaryPanel.add(new JLabel("Thuế (10%):"), gbc);
//        gbc.gridx = 1;
//        summaryPanel.add(txtTax, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        summaryPanel.add(new JLabel("Giảm giá (5%):"), gbc);
//        gbc.gridx = 1;
//        summaryPanel.add(txtDiscount, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        JLabel totalLabel = new JLabel("Tổng thanh toán:");
//        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        summaryPanel.add(totalLabel, gbc);
//        gbc.gridx = 1;
//        summaryPanel.add(txtTotal, gbc);
//
//        rightPanel.add(summaryPanel, BorderLayout.NORTH);
//
//        // Note panel
//        JPanel notePanel = new JPanel(new BorderLayout());
//        notePanel.setBorder(BorderFactory.createTitledBorder("Ghi chú"));
//        notePanel.add(new JScrollPane(txtNote), BorderLayout.CENTER);
//        rightPanel.add(notePanel, BorderLayout.CENTER);
//
//        mainPanel.add(rightPanel, BorderLayout.EAST);
//
//        add(mainPanel, BorderLayout.CENTER);
//
//        // Buttons
//        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        btnPanel.add(btnSave);
//        btnPanel.add(btnCancel);
//        add(btnPanel, BorderLayout.SOUTH);
//    }
//
//    private void addItem() {
//        String serviceName = txtServiceName.getText().trim();
//        if (serviceName.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        try {
//            double unitPrice = Double.parseDouble(txtUnitPrice.getText().trim());
//            int quantity = Integer.parseInt(txtQuantity.getText().trim());
//
//            if (unitPrice < 0 || quantity < 1) {
//                JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải lớn hơn 0!", "Lỗi",
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            double total = unitPrice * quantity;
//            itemsTableModel.addRow(new Object[] {
//                    serviceName,
//                    String.format("%.2f", unitPrice),
//                    quantity,
//                    String.format("%.2f", total)
//            });
//
//            // Clear input fields
//            txtServiceName.setText("");
//            txtUnitPrice.setText("");
//            txtQuantity.setText("");
//
//            calculateTotal();
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số hợp lệ!", "Lỗi",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void removeItem() {
//        int selectedRow = itemsTable.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ để xóa!", "Thông báo",
//                    JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        itemsTableModel.removeRow(selectedRow);
//        calculateTotal();
//    }
//
//    private void calculateTotal() {
//        double subtotal = 0;
//        for (int i = 0; i < itemsTableModel.getRowCount(); i++) {
//            String totalStr = itemsTableModel.getValueAt(i, 3).toString();
//            subtotal += Double.parseDouble(totalStr.replace(",", ""));
//        }
//
//        txtSubTotal.setText(String.format("%.2f", subtotal));
//
//        double tax = subtotal * taxRate;
//        txtTax.setText(String.format("%.2f", tax));
//
//        double discount = subtotal * discountRate;
//        txtDiscount.setText(String.format("%.2f", discount));
//
//        double total = subtotal + tax - discount;
//        txtTotal.setText(String.format("%.2f", total));
//    }
//
//    private void loadInvoiceData() {
//        if (invoice != null) {
//            // Set patient - get from MedicalRecord
//            if (invoice.getMedicalRecord() != null && invoice.getMedicalRecord().getPatient() != null) {
//                int patientId = invoice.getMedicalRecord().getPatient().getId();
//                for (int i = 0; i < patients.size(); i++) {
//                    if (patients.get(i).getId() == patientId) {
//                        patientComboBox.setSelectedIndex(i);
//                        break;
//                    }
//                }
//            }
//
//            // Load items
//            if (invoice.getItems() != null) {
//                for (InvoiceItem item : invoice.getItems()) {
//                    itemsTableModel.addRow(new Object[] {
//                            item.getServiceName(),
//                            String.format("%.2f", item.getUnitPrice()),
//                            item.getQuantity(),
//                            String.format("%.2f", item.getTotal())
//                    });
//                }
//            }
//
//            txtSubTotal.setText(String.format("%.2f", invoice.getSubtotal()));
//            txtTax.setText(String.format("%.2f", invoice.getTax()));
//            txtDiscount.setText(String.format("%.2f", invoice.getDiscount()));
//            txtTotal.setText(String.format("%.2f", invoice.getTotal()));
//            if (invoice.getNote() != null) {
//                txtNote.setText(invoice.getNote());
//            }
//        }
//    }
//
//    private boolean validateInput() {
//        if (patientComboBox.getSelectedIndex() == -1 || patients.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn bệnh nhân!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//
//        if (itemsTableModel.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//
//        return true;
//    }
//
//    public Invoice getInvoice() {
//        if (!saved) {
//            return null;
//        }
//
//        Invoice inv = invoice != null ? invoice : new Invoice();
//
//        // Get selected patient and create MedicalRecord wrapper
//        int selectedIndex = patientComboBox.getSelectedIndex();
//        if (selectedIndex >= 0 && selectedIndex < patients.size()) {
//            Patient selectedPatient = patients.get(selectedIndex);
//            // If invoice doesn't have a MedicalRecord, create a minimal one
//            if (inv.getMedicalRecord() == null) {
//                // Create a minimal MedicalRecord with just patient info
//                com.ptit.dental.model.entity.MedicalRecord mr = new com.ptit.dental.model.entity.MedicalRecord(
//                        0, selectedPatient, null, "", "", "", java.time.LocalDateTime.now());
//                inv.setMedicalRecord(mr);
//            } else {
//                // Update existing MedicalRecord's patient
//                inv.getMedicalRecord().patient = selectedPatient;
//            }
//        }
//
//        inv.setCreatedDate(inv.getCreatedDate() != null ? inv.getCreatedDate() : new Date());
//
//        // Get items
//        List<InvoiceItem> items = new ArrayList<>();
//        for (int i = 0; i < itemsTableModel.getRowCount(); i++) {
//            InvoiceItem item = new InvoiceItem();
//            item.setServiceName(itemsTableModel.getValueAt(i, 0).toString());
//            item.setUnitPrice(Double.parseDouble(itemsTableModel.getValueAt(i, 1).toString().replace(",", "")));
//            item.setQuantity(Integer.parseInt(itemsTableModel.getValueAt(i, 2).toString()));
//            item.setTotal(Double.parseDouble(itemsTableModel.getValueAt(i, 3).toString().replace(",", "")));
//            items.add(item);
//        }
//        inv.setItems(items);
//
//        inv.setSubtotal(Double.parseDouble(txtSubTotal.getText().replace(",", "")));
//        inv.setTax(Double.parseDouble(txtTax.getText().replace(",", "")));
//        inv.setDiscount(Double.parseDouble(txtDiscount.getText().replace(",", "")));
//        inv.setTotal(Double.parseDouble(txtTotal.getText().replace(",", "")));
//        inv.setNote(txtNote.getText().trim());
//
//        return inv;
//    }
//
//    public boolean isSaved() {
//        return saved;
//    }
//}



package com.ptit.dental.view;

import com.ptit.dental.model.dao.PatientDAO;
import com.ptit.dental.model.entity.Invoice;
import com.ptit.dental.model.entity.InvoiceItem;
import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.utils.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceFormDialog extends JDialog {
    private JComboBox<String> patientComboBox;
    private JTable itemsTable;
    private JTextField txtServiceName;
    private JTextField txtUnitPrice;
    private JTextField txtQuantity;
    private JButton btnAddItem;
    private JButton btnRemoveItem;
    private JTextField txtSubTotal;
    private JTextField txtTax;
    private JTextField txtDiscount;
    private JTextField txtTotal;
    private JTextArea txtNote;
    private JButton btnSave;
    private JButton btnCancel;

    private Invoice invoice;
    private boolean saved = false;
    private List<Patient> patients;
    private DefaultTableModel itemsTableModel;
    private double taxRate = 0.10; // 10%
    private double discountRate = 0.05; // 5%

    public InvoiceFormDialog(JFrame parent, Invoice invoice) {
        super(parent, invoice == null ? "Tạo hóa đơn mới" : "Chỉnh sửa hóa đơn", true);
        this.invoice = invoice;
        loadPatients();
        initComponents();
        setupLayout();

        if (invoice != null) {
            loadInvoiceData();
        }

        setLocationRelativeTo(parent);
    }

    private void loadPatients() {
        try {
            Connection conn = Database.getInstance().getConnection();
            PatientDAO patientDAO = new PatientDAO(conn);
            patients = patientDAO.getAll();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách bệnh nhân: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            patients = new ArrayList<>();
        }
    }

    private void initComponents() {
        // Patient combo box
        String[] patientNames = new String[patients.size()];
        for (int i = 0; i < patients.size(); i++) {
            patientNames[i] = patients.get(i).getId() + " - " + patients.get(i).getFullname();
        }
        patientComboBox = new JComboBox<>(patientNames);

        // Items table
        String[] columnNames = {"Dịch vụ", "Đơn giá", "Số lượng", "Thành tiền"};
        itemsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        itemsTable = new JTable(itemsTableModel);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Item input fields
        txtServiceName = new JTextField(15);
        txtUnitPrice = new JTextField(10);
        txtQuantity = new JTextField(10);

        btnAddItem = new JButton("Thêm");
        btnAddItem.addActionListener(e -> addItem());
        btnRemoveItem = new JButton("Xóa");
        btnRemoveItem.addActionListener(e -> removeItem());

        // Summary fields
        txtSubTotal = new JTextField(15);
        txtSubTotal.setEditable(false);
        txtTax = new JTextField(15);
        txtTax.setEditable(false);
        txtDiscount = new JTextField(15);
        txtDiscount.setEditable(false);
        txtTotal = new JTextField(15);
        txtTotal.setEditable(false);

        txtNote = new JTextArea(3, 30);
        txtNote.setLineWrap(true);

        btnSave = new JButton("Lưu");
        btnSave.setBackground(new Color(76, 175, 80));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> {
            if (validateInput()) {
                saved = true;
                dispose();
            }
        });

        btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dispose());

        // Auto calculate is done when items change
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setSize(900, 650);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel - Patient selection (Improved)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thông tin bệnh nhân"),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        JLabel patientLabel = new JLabel("Bệnh nhân:");
        patientLabel.setFont(new Font("Arial", Font.BOLD, 12));
        topPanel.add(patientLabel);
        patientComboBox.setPreferredSize(new Dimension(300, 30));
        topPanel.add(patientComboBox);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel - Items table and input
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Items table
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Danh sách dịch vụ"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        itemsTable.setRowHeight(25);
        itemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        itemsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane tableScroll = new JScrollPane(itemsTable);
        tableScroll.setPreferredSize(new Dimension(0, 200));
        tablePanel.add(tableScroll, BorderLayout.CENTER);
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        // Item input panel - Improved layout
        JPanel itemInputPanel = new JPanel(new BorderLayout(10, 10));
        itemInputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thêm dịch vụ"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        itemInputPanel.setBackground(new Color(0xC0C0C0));

        // Input fields panel with GridBagLayout for better alignment
        JPanel inputFieldsPanel = new JPanel(new GridBagLayout());
        inputFieldsPanel.setOpaque(false);
        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(5, 5, 5, 5);
        gbcInput.anchor = GridBagConstraints.WEST;

        // Row 1: Service Name
        gbcInput.gridx = 0; gbcInput.gridy = 0;
        gbcInput.weightx = 0;
        inputFieldsPanel.add(new JLabel("Tên dịch vụ:"), gbcInput);
        gbcInput.gridx = 1;
        gbcInput.weightx = 1.0;
        gbcInput.fill = GridBagConstraints.HORIZONTAL;
        txtServiceName.setPreferredSize(new Dimension(200, 25));
        inputFieldsPanel.add(txtServiceName, gbcInput);

        // Row 2: Unit Price and Quantity
        gbcInput.gridx = 0; gbcInput.gridy = 1;
        gbcInput.weightx = 0;
        gbcInput.fill = GridBagConstraints.NONE;
        inputFieldsPanel.add(new JLabel("Đơn giá:"), gbcInput);
        gbcInput.gridx = 1;
        gbcInput.weightx = 0.5;
        gbcInput.fill = GridBagConstraints.HORIZONTAL;
        txtUnitPrice.setPreferredSize(new Dimension(120, 25));
        inputFieldsPanel.add(txtUnitPrice, gbcInput);

        gbcInput.gridx = 2;
        gbcInput.insets = new Insets(5, 15, 5, 5);
        inputFieldsPanel.add(new JLabel("Số lượng:"), gbcInput);
        gbcInput.gridx = 3;
        gbcInput.weightx = 0.5;
        gbcInput.insets = new Insets(5, 5, 5, 5);
        txtQuantity.setPreferredSize(new Dimension(100, 25));
        inputFieldsPanel.add(txtQuantity, gbcInput);

        itemInputPanel.add(inputFieldsPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonsPanel.setOpaque(false);
        btnAddItem.setPreferredSize(new Dimension(100, 30));
        btnAddItem.setBackground(new Color(33, 150, 243));
        btnAddItem.setForeground(Color.WHITE);
        btnAddItem.setFocusPainted(false);
        btnRemoveItem.setPreferredSize(new Dimension(100, 30));
        btnRemoveItem.setBackground(new Color(244, 67, 54));
        btnRemoveItem.setForeground(Color.WHITE);
        btnRemoveItem.setFocusPainted(false);
        buttonsPanel.add(btnAddItem);
        buttonsPanel.add(btnRemoveItem);
        itemInputPanel.add(buttonsPanel, BorderLayout.SOUTH);

        centerPanel.add(itemInputPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Right panel - Summary and Note
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Tổng kết"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        summaryPanel.add(new JLabel("Tổng tiền:"), gbc);
        gbc.gridx = 1;
        summaryPanel.add(txtSubTotal, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        summaryPanel.add(new JLabel("Thuế (10%):"), gbc);
        gbc.gridx = 1;
        summaryPanel.add(txtTax, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        summaryPanel.add(new JLabel("Giảm giá (5%):"), gbc);
        gbc.gridx = 1;
        summaryPanel.add(txtDiscount, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel totalLabel = new JLabel("Tổng thanh toán:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(totalLabel, gbc);
        gbc.gridx = 1;
        summaryPanel.add(txtTotal, gbc);

        rightPanel.add(summaryPanel, BorderLayout.NORTH);

        // Note panel
        JPanel notePanel = new JPanel(new BorderLayout());
        notePanel.setBorder(BorderFactory.createTitledBorder("Ghi chú"));
        notePanel.add(new JScrollPane(txtNote), BorderLayout.CENTER);
        rightPanel.add(notePanel, BorderLayout.CENTER);

        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void addItem() {
        String serviceName = txtServiceName.getText().trim();
        if (serviceName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double unitPrice = Double.parseDouble(txtUnitPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());

            if (unitPrice < 0 || quantity < 1) {
                JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double total = unitPrice * quantity;
            itemsTableModel.addRow(new Object[]{
                    serviceName,
                    String.format("%.2f", unitPrice),
                    quantity,
                    String.format("%.2f", total)
            });

            // Clear input fields
            txtServiceName.setText("");
            txtUnitPrice.setText("");
            txtQuantity.setText("");

            calculateTotal();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeItem() {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        itemsTableModel.removeRow(selectedRow);
        calculateTotal();
    }

    private void calculateTotal() {
        double subtotal = 0;
        for (int i = 0; i < itemsTableModel.getRowCount(); i++) {
            String totalStr = itemsTableModel.getValueAt(i, 3).toString();
            subtotal += Double.parseDouble(totalStr.replace(",", ""));
        }

        txtSubTotal.setText(String.format("%.2f", subtotal));

        double tax = subtotal * taxRate;
        txtTax.setText(String.format("%.2f", tax));

        double discount = subtotal * discountRate;
        txtDiscount.setText(String.format("%.2f", discount));

        double total = subtotal + tax - discount;
        txtTotal.setText(String.format("%.2f", total));
    }

    private void loadInvoiceData() {
        if (invoice != null) {
            // Set patient
            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getId() == invoice.getPatientId()) {
                    patientComboBox.setSelectedIndex(i);
                    break;
                }
            }

            // Load items
            if (invoice.getItems() != null) {
                for (InvoiceItem item : invoice.getItems()) {
                    itemsTableModel.addRow(new Object[]{
                            item.getServiceName(),
                            String.format("%.2f", item.getUnitPrice()),
                            item.getQuantity(),
                            String.format("%.2f", item.getTotal())
                    });
                }
            }

            txtSubTotal.setText(String.format("%.2f", invoice.getSubtotal()));
            txtTax.setText(String.format("%.2f", invoice.getTax()));
            txtDiscount.setText(String.format("%.2f", invoice.getDiscount()));
            txtTotal.setText(String.format("%.2f", invoice.getTotal()));
            if (invoice.getNote() != null) {
                txtNote.setText(invoice.getNote());
            }
        }
    }

    private boolean validateInput() {
        if (patientComboBox.getSelectedIndex() == -1 || patients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bệnh nhân!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (itemsTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public Invoice getInvoice() {
        if (!saved) {
            return null;
        }

        Invoice inv = invoice != null ? invoice : new Invoice();

        // Get selected patient
        int selectedIndex = patientComboBox.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < patients.size()) {
            inv.setPatientId(patients.get(selectedIndex).getId());
        }

        inv.setCreatedDate(inv.getCreatedDate() != null ? inv.getCreatedDate() : new Date());

        // Get items
        List<InvoiceItem> items = new ArrayList<>();
        for (int i = 0; i < itemsTableModel.getRowCount(); i++) {
            InvoiceItem item = new InvoiceItem();
            item.setServiceName(itemsTableModel.getValueAt(i, 0).toString());
            item.setUnitPrice(Double.parseDouble(itemsTableModel.getValueAt(i, 1).toString().replace(",", "")));
            item.setQuantity(Integer.parseInt(itemsTableModel.getValueAt(i, 2).toString()));
            item.setTotal(Double.parseDouble(itemsTableModel.getValueAt(i, 3).toString().replace(",", "")));
            items.add(item);
        }
        inv.setItems(items);

        inv.setSubtotal(Double.parseDouble(txtSubTotal.getText().replace(",", "")));
        inv.setTax(Double.parseDouble(txtTax.getText().replace(",", "")));
        inv.setDiscount(Double.parseDouble(txtDiscount.getText().replace(",", "")));
        inv.setTotal(Double.parseDouble(txtTotal.getText().replace(",", "")));
        inv.setNote(txtNote.getText().trim());

        return inv;
    }

    public boolean isSaved() {
        return saved;
    }
}

