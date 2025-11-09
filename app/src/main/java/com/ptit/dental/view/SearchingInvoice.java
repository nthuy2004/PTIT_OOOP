package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SearchingInvoice extends BaseView {
    private JTextField searchField;
    private JButton searchButton;
    private JTable invoiceTable;
    private JLabel titleLabel;
    private JButton btnHome;
    public SearchingInvoice() {
        initComponents();
        setupLayout();
    }
    private void initComponents() {
        titleLabel = new JLabel("QUẢN LÝ VIỆN PHÍ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

         // Search components
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm");
        searchButton.setBackground(new Color(255, 87, 34)); // Orange color
        searchButton.setForeground(Color.WHITE);

        // Nút quay về trang chủ
        btnHome = new JButton("Quay về trang chủ");
        btnHome.setBackground(new Color(76, 175, 80));
        btnHome.setForeground(Color.WHITE);
        btnHome.setFocusPainted(false);
        btnHome.addActionListener(e -> dispose());

        // Table
        String[] columnNames = {"Mã hóa đơn", "Tên bệnh nhân", "Ngày lập", "Tổng tiền"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        invoiceTable = new JTable(model);
    }
    private void setupLayout() {
       setLayout(new BorderLayout(10, 10));
        
        // Top panel with icon and title
        JPanel topPanel = new JPanel(new BorderLayout());
        ImageIcon icon = new ImageIcon("path/to/your/icon.png"); // Add your icon path
        JLabel iconLabel = new JLabel(icon);
        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(btnHome);
        searchPanel.add(leftPanel, BorderLayout.WEST);
        searchPanel.add(rightPanel, BorderLayout.EAST);

        // Combine top elements
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table panel
        JScrollPane tableScrollPane = new JScrollPane(invoiceTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Add all components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Set frame properties
        setTitle("Quản lý răng - Viện phí");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    // Method to add data to table
    public void addInvoiceToTable(String id, String name, String date, double amount) {
        DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        model.addRow(new Object[]{id, name, date, String.format("%.2f", amount)});
    }

    // Getters for components
    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTable getInvoiceTable() {
        return invoiceTable;
    } 
    }
