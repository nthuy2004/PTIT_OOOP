package com.ptit.dental.view;

import com.ptit.dental.model.entity.Medicine;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class MedicineFormDialog extends JDialog {
    private JTextField txtName;
    private JDateChooser importDateChooser;
    private JDateChooser expiryDateChooser;
    private JTextField txtPrice;
    private JTextField txtQuantity;
    private JButton btnSave;
    private JButton btnCancel;
    
    private Medicine medicine;
    private boolean saved = false;

    public MedicineFormDialog(JFrame parent, Medicine medicine) {
        super(parent, medicine == null ? "Thêm thuốc mới" : "Chỉnh sửa thuốc", true);
        this.medicine = medicine;
        initComponents();
        setupLayout();
        
        if (medicine != null) {
            loadMedicineData();
        }
        
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        txtName = new JTextField(20);
        importDateChooser = new JDateChooser();
        importDateChooser.setDateFormatString("dd/MM/yyyy");
        importDateChooser.setDate(new Date());
        
        expiryDateChooser = new JDateChooser();
        expiryDateChooser.setDateFormatString("dd/MM/yyyy");
        
        txtPrice = new JTextField(20);
        txtQuantity = new JTextField(20);
        
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
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setSize(450, 300);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Tên thuốc
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tên thuốc:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtName, gbc);
        
        // Ngày nhập
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Ngày nhập:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(importDateChooser, gbc);
        
        // Ngày hết hạn
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Ngày hết hạn:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(expiryDateChooser, gbc);
        
        // Giá
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Giá:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtPrice, gbc);
        
        // Số lượng
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Số lượng tồn kho:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtQuantity, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadMedicineData() {
        if (medicine != null) {
            txtName.setText(medicine.getName());
            importDateChooser.setDate(medicine.getImportDate());
            expiryDateChooser.setDate(medicine.getExpiryDate());
            txtPrice.setText(String.valueOf(medicine.getPrice()));
            txtQuantity.setText(String.valueOf(medicine.getQuantity()));
        }
    }

    private boolean validateInput() {
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thuốc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (importDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (expiryDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày hết hạn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Giá phải lớn hơn hoặc bằng 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn hoặc bằng 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Medicine getMedicine() {
        if (!saved) {
            return null;
        }
        
        Medicine m = medicine != null ? medicine : new Medicine();
        m.setName(txtName.getText().trim());
        m.setImportDate(importDateChooser.getDate());
        m.setExpiryDate(expiryDateChooser.getDate());
        m.setPrice(Double.parseDouble(txtPrice.getText().trim()));
        m.setQuantity(Integer.parseInt(txtQuantity.getText().trim()));
        
        return m;
    }

    public boolean isSaved() {
        return saved;
    }
}

