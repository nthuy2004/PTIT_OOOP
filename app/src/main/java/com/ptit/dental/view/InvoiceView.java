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

    public InvoiceView() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        // Title setup
        titleLabel = new JLabel("QUẢN LÝ VIỆN PHÍ");
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
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/ptit/dental/image/bin.png"));
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
        setTitle("TWP clinic management - Viện phí");
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