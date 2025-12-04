package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;
import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

public class MedicalRecordView extends BaseView {
    public JDateChooser dateChooser;
    public JTextField namePatientField;
    public JComboBox<String> statusComboBox;
    public JComboBox<String> doctorComboBox;
    public JComboBox<String> diseaseTypeComboBox;
    public JTextArea diagnosticArea;
    public JTextArea planArea;
    public JButton saveButton;
    public JButton cancelButton;

    public MedicalRecordView() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        // Initialize date chooser
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");

        // Initialize patient name field
        namePatientField = new JTextField(20);

        // Initialize doctor combo box
        String[] doctors = { "Dr. Smith", "Dr. Johnson", "Dr. Williams" }; // Add your doctor names
        doctorComboBox = new JComboBox<>(doctors);

        // Initialize disease type combo box
        String[] diseaseTypes = { "Cavity", "Gum Disease", "Root Canal", "Tooth Extraction", "Cleaning" }; // Add your
                                                                                                           // disease
                                                                                                           // types
        diseaseTypeComboBox = new JComboBox<>(diseaseTypes);

        // Initialize status combo box
        String[] statusOptions = { "Not Yet", "Done", "Cancel" };
        statusComboBox = new JComboBox<>(statusOptions);

        // Initialize text areas
        diagnosticArea = new JTextArea(5, 20);
        diagnosticArea.setLineWrap(true);
        diagnosticArea.setWrapStyleWord(true);

        planArea = new JTextArea(5, 20);
        planArea.setLineWrap(true);
        planArea.setWrapStyleWord(true);

        // Initialize buttons
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
    }

    private void setupLayout() {
        setTitle("Medical Record");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        // title
        JLabel titleLabel = new JLabel("Medical Record");
        titleLabel.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);
        // Create main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Patient Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(namePatientField, gbc);

        // Doctor Selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Doctor:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(doctorComboBox, gbc);

        // Disease Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Disease Type:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(diseaseTypeComboBox, gbc);

        // Date Chooser
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Examination Date:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(dateChooser, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(statusComboBox, gbc);

        // Diagnostic
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Diagnostic:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(diagnosticArea), gbc);

        // Plan
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weighty = 1.0;
        mainPanel.add(new JLabel("Plan:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(new JScrollPane(planArea), gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to frame
        add(mainPanel);
    }
}