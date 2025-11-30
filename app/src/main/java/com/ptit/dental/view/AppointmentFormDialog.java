package com.ptit.dental.view;

import com.ptit.dental.model.entity.Appointment;
import com.ptit.dental.model.entity.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentFormDialog extends JDialog {

    private boolean saved = false;
    private Appointment appointment;

    private JTextField tfPatientName;
    private JTextField tfDate; // dd/MM/yyyy
    private JTextField tfTime; // HH:mm
    private JTextField tfService;
    private JTextArea taNote;

    public AppointmentFormDialog(SearchingAppointment parent, Appointment appointment) {
        super(parent, true);
        this.appointment = appointment;
        initUI();
        if (appointment != null)
            populate(appointment);
    }

    private void initUI() {
        setTitle("Form Lịch Hẹn");
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(5, 2, 6, 6));
        form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        form.add(new JLabel("Tên bệnh nhân:"));
        tfPatientName = new JTextField();
        form.add(tfPatientName);

        form.add(new JLabel("Ngày (dd/MM/yyyy):"));
        tfDate = new JTextField();
        form.add(tfDate);

        form.add(new JLabel("Giờ (HH:mm):"));
        tfTime = new JTextField();
        form.add(tfTime);

        form.add(new JLabel("Dịch vụ:"));
        tfService = new JTextField();
        form.add(tfService);

        form.add(new JLabel("Ghi chú:"));
        taNote = new JTextArea(5, 2);
        form.add(new JScrollPane(taNote));

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> onCancel());

        buttons.add(btnSave);
        buttons.add(btnCancel);
        add(buttons, BorderLayout.SOUTH);

        setSize(420, 300);
        setLocationRelativeTo(getOwner());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saved = false;
            }
        });
    }

    private void populate(Appointment app) {
        if (app == null)
            return;
        tfPatientName.setText(app.getPatientName());
        if (app.getDate() != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ld = LocalDate.ofInstant(app.getDate().toInstant(), java.time.ZoneId.systemDefault());
            tfDate.setText(ld.format(df));
        }
        tfTime.setText(app.getTime());
        tfService.setText(app.getService());
        taNote.setText(app.getNote());
    }

    private void onSave() {
        try {
            String name = tfPatientName.getText().trim();
            String dateStr = tfDate.getText().trim();
            String timeStr = tfTime.getText().trim();
            String service = tfService.getText().trim();
            // note ignored for now

            LocalDate ld = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime lt = LocalTime.parse(timeStr);
            LocalDateTime ldt = LocalDateTime.of(ld, lt);

            Patient p = new Patient();
            p.setFullname(name);

            if (appointment == null) {
                appointment = new Appointment(null, ldt, service, p, null);
            } else {
                appointment.time = ldt;
                appointment.reason = service;
                appointment.patient = p;
            }

            saved = true;
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        saved = false;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}
