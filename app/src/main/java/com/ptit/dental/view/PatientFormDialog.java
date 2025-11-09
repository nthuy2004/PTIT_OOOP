package com.ptit.dental.view;

import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.model.enums.Gender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class PatientFormDialog extends JDialog {
    private JTextField txtFullname;
    private JDateChooser dateChooser;
    private JComboBox<String> genderComboBox;
    private JTextField txtAddress;
    private JTextField txtPhone;
    private JButton btnSave;
    private JButton btnCancel;
    
    private Patient patient;
    private boolean saved = false;

    public PatientFormDialog(JFrame parent, Patient patient) {
        super(parent, patient == null ? "Thêm bệnh nhân mới" : "Chỉnh sửa bệnh nhân", true);
        this.patient = patient;
        initComponents();
        setupLayout();
        
        if (patient != null) {
            loadPatientData();
        }
        
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        txtFullname = new JTextField(20);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(new Date());
        
        String[] genders = {"Nam", "Nữ"};
        genderComboBox = new JComboBox<>(genders);
        
        txtAddress = new JTextField(20);
        txtPhone = new JTextField(20);
        
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
        
        // Họ và tên
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtFullname, gbc);
        
        // Ngày sinh
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(dateChooser, gbc);
        
        // Giới tính
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(genderComboBox, gbc);
        
        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtAddress, gbc);
        
        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtPhone, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadPatientData() {
        if (patient != null) {
            txtFullname.setText(patient.getFullname());
            dateChooser.setDate(patient.getBirthday());
            if (patient.getGender() == Gender.NAM) {
                genderComboBox.setSelectedIndex(0);
            } else if (patient.getGender() == Gender.NU) {
                genderComboBox.setSelectedIndex(1);
            }
            txtAddress.setText(patient.getAddress());
            txtPhone.setText(patient.getPhone());
        }
    }

    private boolean validateInput() {
        if (txtFullname.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ và tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtPhone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Patient getPatient() {
        if (!saved) {
            return null;
        }
        
        Patient p = patient != null ? patient : new Patient();
        p.setFullname(txtFullname.getText().trim());
        p.setBirthday(dateChooser.getDate());
        String genderStr = (String) genderComboBox.getSelectedItem();
        p.setGender(genderStr.equals("Nam") ? Gender.NAM : Gender.NU);
        p.setAddress(txtAddress.getText().trim());
        p.setPhone(txtPhone.getText().trim());
        
        return p;
    }

    public boolean isSaved() {
        return saved;
    }
}

