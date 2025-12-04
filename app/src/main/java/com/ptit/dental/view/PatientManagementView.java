package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientManagementView extends BaseView {
    private JTextField searchField;
    private JButton searchButton;
    private JTable patientTable;
    private JLabel titleLabel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton btnHome;

    public PatientManagementView() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        // Title setup
        titleLabel = new JLabel("QUẢN LÝ BỆNH NHÂN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Search components
        searchField = new JTextField(30);
        searchButton = new JButton("Tìm");
        searchButton.setBackground(new Color(33, 150, 243));
        searchButton.setForeground(Color.WHITE);

        String imagePath = "/com/ptit/dental/image/";

        // Action buttons
        addButton = new JButton();
        addButton.setIcon(new ImageIcon(getClass().getResource(imagePath + "plus.png")));
        addButton.setToolTipText("Thêm bệnh nhân");

        deleteButton = new JButton();
        deleteButton.setIcon(new ImageIcon(getClass().getResource(imagePath + "bin.png")));
        deleteButton.setToolTipText("Xóa bệnh nhân");

        editButton = new JButton();
        editButton.setIcon(new ImageIcon(getClass().getResource(imagePath + "compose.png")));
        editButton.setToolTipText("Sửa thông tin bệnh nhân");

        // Nút quay về trang chủ
        btnHome = new JButton("Quay về trang chủ");
        btnHome.setBackground(new Color(76, 175, 80));
        btnHome.setForeground(Color.WHITE);
        btnHome.setFocusPainted(false);
        btnHome.addActionListener(e -> dispose());

        // Table setup
        String[] columnNames = { "Mã BN", "Họ và tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Số điện thoại" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table read-only
            }
        };
        patientTable = new JTable(model);
        patientTable.getTableHeader().setReorderingAllowed(false);
        patientTable.setRowHeight(25);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Top panel with icon and title
        JPanel topPanel = new JPanel(new BorderLayout());
        ImageIcon icon = new ImageIcon("path/to/patient-icon.png"); // Replace with your icon path
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

        // Combine header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(topPanel, BorderLayout.NORTH);
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.add(searchPanel, BorderLayout.WEST);
        middlePanel.add(actionPanel, BorderLayout.EAST);
        headerPanel.add(middlePanel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table scroll
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Add to frame
        add(headerPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Frame properties
        setTitle("Quản lý răng - Bệnh nhân");
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

    public JTable getPatientTable() {
        return patientTable;
    }

    // Method to add patient to table
    public void addPatientToTable(String id, String fullName, String birthDate,
            String gender, String address, String phone) {
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.addRow(new Object[] { id, fullName, birthDate, gender, address, phone });
    }
}
