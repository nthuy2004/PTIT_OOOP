package com.ptit.dental.model.dao;

import com.ptit.dental.model.entity.Appointment;
import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.utils.Database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    private Connection providedConn = null;

    public AppointmentDAO() {
        // no-arg constructor (controllers previously constructed with no args)
    }

    public AppointmentDAO(Connection conn) {
        this.providedConn = conn;
    }

    private Connection getConn() throws SQLException {
        return providedConn != null ? providedConn : Database.getInstance().getConnection();
    }

    public List<Appointment> getAll() throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.id, a.time, a.reason, p.id as patient_id, p.fullname as patient_name FROM appointment a LEFT JOIN patients p ON a.patient_id = p.id ORDER BY a.id DESC";
        try (PreparedStatement ps = getConn().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp ts = rs.getTimestamp("time");
                LocalDateTime time = ts != null ? ts.toLocalDateTime() : null;
                String reason = rs.getString("reason");

                Patient patient = null;
                int pid = rs.getInt("patient_id");
                if (pid > 0) {
                    patient = new Patient();
                    patient.setId(pid);
                    patient.setFullname(rs.getString("patient_name"));
                }

                Appointment a = new Appointment(id, time, reason, patient, null);
                list.add(a);
            }
        }
        return list;
    }

    public List<Appointment> searchByPatientName(String keyword) throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.id, a.time, a.reason, p.id as patient_id, p.fullname as patient_name FROM appointment a LEFT JOIN patients p ON a.patient_id = p.id WHERE LOWER(p.fullname) LIKE ? ORDER BY a.id DESC";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Timestamp ts = rs.getTimestamp("time");
                    LocalDateTime time = ts != null ? ts.toLocalDateTime() : null;
                    String reason = rs.getString("reason");

                    Patient patient = null;
                    int pid = rs.getInt("patient_id");
                    if (pid > 0) {
                        patient = new Patient();
                        patient.setId(pid);
                        patient.setFullname(rs.getString("patient_name"));
                    }

                    Appointment a = new Appointment(id, time, reason, patient, null);
                    list.add(a);
                }
            }
        }
        return list;
    }

    public void insert(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointment (`patient_id`, time, reason) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            Integer pid = appointment.patient != null ? appointment.patient.getId() : 0;
            if (pid == 0 && appointment.getPatientName() != null && !appointment.getPatientName().isEmpty()) {
                // Try to find patient by name, else leave null (0)
            }
            if (appointment.time != null)
                ps.setTimestamp(1,
                        new Timestamp(appointment.time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            else
                ps.setTimestamp(1, null);
            // patient id
            ps.setInt(1, pid);
            ps.setTimestamp(2, appointment.time != null ? Timestamp.valueOf(appointment.time) : null);
            ps.setString(3, appointment.getService());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    appointment.id = rs.getInt(1);
                }
            }
        }
    }

    public Appointment getById(int id) throws SQLException {
        String sql = "SELECT a.id, a.time, a.reason, p.id as patient_id, p.fullname as patient_name FROM appointment a LEFT JOIN patients p ON a.patient_id = p.id WHERE a.id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp ts = rs.getTimestamp("time");
                    LocalDateTime time = ts != null ? ts.toLocalDateTime() : null;
                    String reason = rs.getString("reason");
                    Patient patient = null;
                    int pid = rs.getInt("patient_id");
                    if (pid > 0) {
                        patient = new Patient();
                        patient.setId(pid);
                        patient.setFullname(rs.getString("patient_name"));
                    }
                    return new Appointment(id, time, reason, patient, null);
                }
            }
        }
        return null;
    }

    public void update(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointment SET patient_id = ?, time = ?, reason = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            int pid = appointment.patient != null ? appointment.patient.getId() : 0;
            ps.setInt(1, pid);
            ps.setTimestamp(2, appointment.time != null ? Timestamp.valueOf(appointment.time) : null);
            ps.setString(3, appointment.getService());
            ps.setInt(4, appointment.id != null ? appointment.id : 0);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM appointment WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
