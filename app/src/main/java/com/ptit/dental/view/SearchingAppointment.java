package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SearchingAppointment extends BaseView {
    private JTextField searchField;
    private JButton searchButton;
    private JTable appointmentTable;
    private JButton btnHome;
    private JButton btnEditAppointment;
    private JButton btnDeleteAppointment;
    private JButton btnAddAppointment;
    private JLabel titleLabel;

    public SearchingAppointment() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {

        titleLabel = new JLabel("QUẢN LÝ LỊCH HẸN BỆNH NHÂN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Search
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm");
        searchButton.setBackground(new Color(33, 150, 243));
        searchButton.setForeground(Color.WHITE);

        // Add appointment button
        btnAddAppointment = new JButton("Tạo lịch hẹn mới");
        btnAddAppointment.setBackground(new Color(76, 175, 80));
        btnAddAppointment.setForeground(Color.WHITE);

        // Edit / Delete buttons
        btnEditAppointment = new JButton("Sửa");
        btnEditAppointment.setBackground(new Color(255, 193, 7));
        btnEditAppointment.setForeground(Color.BLACK);

        btnDeleteAppointment = new JButton("Xóa");
        btnDeleteAppointment.setBackground(new Color(244, 67, 54));
        btnDeleteAppointment.setForeground(Color.WHITE);

        // Home button
        btnHome = new JButton("Trang chủ");
        btnHome.setBackground(new Color(244, 67, 54));
        btnHome.setForeground(Color.WHITE);
        btnHome.addActionListener(e -> dispose());

        // Table
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
        appointmentTable = new JTable(model);
        appointmentTable.setRowHeight(25);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // HEADER
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // SEARCH PANEL
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // BUTTON PANEL (Right)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(btnAddAppointment);
        rightPanel.add(btnEditAppointment);
        rightPanel.add(btnDeleteAppointment);
        rightPanel.add(btnHome);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // TABLE
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // MAIN ADD
        add(headerPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // PROPERTIES
        setSize(900, 600);
        setLocationRelativeTo(null);
        setTitle("Quản lý lịch hẹn");
    }

    // Add new appointment row
    public void addAppointmentToTable(String id, String name, String date, String time, String service, String note) {
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.addRow(new Object[] { id, name, date, time, service, note });
    }

    // Getters
    public JButton getBtnAddAppointment() {
        return btnAddAppointment;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTable getAppointmentTable() {
        return appointmentTable;
    }

    public JButton getBtnEditAppointment() {
        return btnEditAppointment;
    }

    public JButton getBtnDeleteAppointment() {
        return btnDeleteAppointment;
    }
}
