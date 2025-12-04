package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;
import com.ptit.dental.model.dao.PatientDAO;
import com.ptit.dental.model.entity.Appointment;
import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.utils.Injector;
import com.ptit.dental.view.component.PatientPicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentFormDialog extends JDialog {

    private boolean saved = false;
    private Appointment appointment;

    private PatientPicker tfPatientName;
    private JTextField tfDate; // dd/MM/yyyy
    private JTextField tfTime; // HH:mm
    private JTextField tfReason;
    private JTextArea taNote;

    public AppointmentFormDialog(BaseView parent, Appointment appointment) {
        super(parent, true);
        this.appointment = appointment;
        initUI();
        if (appointment != null)
            populate(appointment);
    }
    
    public static AppointmentFormDialog Create(BaseView parent, Patient patient) {
        AppointmentFormDialog a = new AppointmentFormDialog(parent, null);
        a.tfPatientName.setSelectedById(patient.getId());
        return a;
    }

    private void initUI() {
        setTitle("Lịch hẹn");
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(5, 2, 6, 6));
        form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        form.add(new JLabel("Tên bệnh nhân:"));
        
        PatientDAO pDAO = Injector.get(PatientDAO.class);
        try {
            tfPatientName = new PatientPicker(pDAO.getAll());
            if(this.appointment != null)
            {
                tfPatientName.setSelectedById(this.appointment.getPatient().getId());
            }
            form.add(tfPatientName);
        }
        catch(SQLException ex){}

        form.add(new JLabel("Ngày (dd/MM/yyyy):"));
        tfDate = new JTextField();
        form.add(tfDate);

        form.add(new JLabel("Giờ (HH:mm):"));
        tfTime = new JTextField();
        form.add(tfTime);

        form.add(new JLabel("Dịch vụ:"));
        tfReason = new JTextField();
        form.add(tfReason);

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
        tfPatientName.setSelectedById(app.getPatient().getId());
        if (app.getDate() != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ld = LocalDate.ofInstant(app.getDate().toInstant(), java.time.ZoneId.systemDefault());
            tfDate.setText(ld.format(df));
        }
        tfTime.setText(app.getTimeOfDatetime());
        tfReason.setText(app.getReason());
        taNote.setText("");
    }

    private void onSave() {
        try {
            Patient patient = tfPatientName.getSelectedPatient();
            String dateStr = tfDate.getText().trim();
            String timeStr = tfTime.getText().trim();
            String reason = tfReason.getText().trim();
            // note ignored for now

            LocalDate ld = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime lt = LocalTime.parse(timeStr);
            LocalDateTime ldt = LocalDateTime.of(ld, lt);

            if (appointment == null) {
                appointment = new Appointment(null, ldt, reason, patient, null);
            } else {
                appointment.setTime(ldt);
                appointment.setReason(reason);
                appointment.setPatient(patient);
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
