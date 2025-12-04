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
    private StaffDAO staffDAO;

    public AppointmentDAO() {
        // no-arg constructor (controllers previously constructed with no args)
    }

    public AppointmentDAO(Connection conn) {
        this.providedConn = conn;
        try {
            this.staffDAO = new StaffDAO(conn);
        } catch (Exception e) {
            // Ignore if connection is null
        }
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
        // Lấy doctor_id mặc định
        int doctorId = getDefaultDoctorId();
        
        // Tìm hoặc lấy patient_id
        int patientId = getOrFindPatientId(appointment);
        if (patientId <= 0) {
            throw new SQLException("Không tìm thấy bệnh nhân");
        }
        
        String sql = "INSERT INTO appointment (`patient_id`, `doctor_id`, time, reason) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setTimestamp(3, appointment.getTime() != null ? Timestamp.valueOf(appointment.getTime()) : null);
            ps.setString(4, appointment.getReason() != null ? appointment.getReason() : "");

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    appointment.setId(rs.getInt(1));
                }
            }
        }
    }
    
    /**
     * Tìm patient_id từ appointment (từ patient object hoặc tên)
     */
    private int getOrFindPatientId(Appointment appointment) throws SQLException {
        // Nếu có patient object với ID hợp lệ
        if (appointment.getPatient() != null && appointment.getPatient().getId() > 0) {
            return appointment.getPatient().getId();
        }
        
        // Nếu có tên bệnh nhân, tìm theo tên
        if (appointment.getPatient().getFullname() != null && !appointment.getPatient().getFullname().trim().isEmpty()) {
            String sql = "SELECT id FROM patients WHERE fullname = ? LIMIT 1";
            try (PreparedStatement ps = getConn().prepareStatement(sql)) {
                ps.setString(1, appointment.getPatient().getFullname().trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            }
        }
        
        return 0; // Không tìm thấy
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
        // Lấy doctor_id mặc định nếu chưa có
        int doctorId = getDefaultDoctorId();
        
        // Tìm hoặc lấy patient_id
        int patientId = getOrFindPatientId(appointment);
        if (patientId <= 0) {
            throw new SQLException("Không tìm thấy bệnh nhân với tên: " + 
                (appointment.getPatient().getFullname() != null ? appointment.getPatient().getFullname() : "N/A") + 
                ". Vui lòng tạo bệnh nhân trước khi cập nhật lịch hẹn.");
        }
        
        String sql = "UPDATE appointment SET patient_id = ?, doctor_id = ?, time = ?, reason = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setTimestamp(3, appointment.getTime() != null ? Timestamp.valueOf(appointment.getTime()) : null);
            ps.setString(4, appointment.getReason() != null ? appointment.getReason() : "");
            ps.setInt(5, appointment.getId() != null ? appointment.getId() : 0);
            ps.executeUpdate();
        }
    }
    
    /**
     * Lấy doctor_id mặc định (staff đầu tiên, ưu tiên role = 2 là BACSI)
     */
    private int getDefaultDoctorId() throws SQLException {
        Connection conn = getConn();
        
        // Ưu tiên tìm staff có role = 2 (BACSI)
        String sql = "SELECT id FROM staff WHERE role = 2 ORDER BY id ASC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        
        // Nếu không có doctor, lấy staff đầu tiên
        String fallbackSql = "SELECT id FROM staff ORDER BY id ASC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(fallbackSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        
        // Nếu không có staff nào, throw exception
        throw new SQLException("Không có staff nào trong database. Vui lòng thêm ít nhất một staff.");
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM appointment WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Lấy danh sách lịch hẹn theo bệnh nhân
    public List<Appointment> getByPatientId(int patientId) throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.id, a.time, a.reason, p.id as patient_id, p.fullname as patient_name " +
                     "FROM appointment a LEFT JOIN patients p ON a.patient_id = p.id WHERE a.patient_id = ? ORDER BY a.id DESC";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, patientId);
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
}
