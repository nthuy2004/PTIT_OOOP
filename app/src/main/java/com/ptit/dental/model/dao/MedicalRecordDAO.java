/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.dao;

import com.ptit.dental.model.entity.MedicalRecord;
import com.ptit.dental.model.entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class MedicalRecordDAO {

    private Connection conn;
    private PatientDAO patientDAO;

    public MedicalRecordDAO(Connection conn) {
        this.conn = conn;
        this.patientDAO = new PatientDAO(conn);
    }

    public MedicalRecord getById(int id) throws SQLException {
        String sql = "SELECT * FROM medical_records WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buildMedicalRecord(rs);
                }
            }
        }
        return null;
    }

    public List<MedicalRecord> getAll() throws SQLException {
        String sql = "SELECT * FROM medical_records";
        List<MedicalRecord> records = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                records.add(buildMedicalRecord(rs));
            }
        }
        return records;
    }

    public List<MedicalRecord> getByPatientId(int patientId) throws SQLException {
        String sql = "SELECT * FROM medical_records WHERE patient_id = ?";
        List<MedicalRecord> records = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records.add(buildMedicalRecord(rs));
                }
            }
        }
        return records;
    }

    public void insert(MedicalRecord record) throws SQLException {
        String sql = "INSERT INTO medical_records (patient_id, doctor_id, diagnostic, plan, status, time) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, record.getPatient().getId());
            ps.setInt(2, record.getDoctor() != null ? record.getDoctor().getId() : 0);
            ps.setString(3, record.getDiagnostic());
            ps.setString(4, record.getPlan());
            ps.setString(5, record.getStatus());
            ps.setObject(6, record.getTime());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    record.id = rs.getInt(1);
                }
            }
        }
    }

    public boolean update(MedicalRecord record) throws SQLException {
        String sql = "UPDATE medical_records SET patient_id = ?, doctor_id = ?, diagnostic = ?, plan = ?, status = ?, time = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, record.getPatient().getId());
            ps.setInt(2, record.getDoctor() != null ? record.getDoctor().getId() : 0);
            ps.setString(3, record.getDiagnostic());
            ps.setString(4, record.getPlan());
            ps.setString(5, record.getStatus());
            ps.setObject(6, record.getTime());
            ps.setInt(7, record.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM medical_records WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private MedicalRecord buildMedicalRecord(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int patientId = rs.getInt("patient_id");
        int doctorId = rs.getInt("doctor_id");
        String diagnostic = rs.getString("diagnostic");
        String plan = rs.getString("plan");
        String status = rs.getString("status");
        LocalDateTime time = rs.getObject("time", LocalDateTime.class);

        // Load patient
        Patient patient = patientDAO.getById(patientId);

        // For now, doctor is null. You can implement StaffDAO similarly if needed
        return new MedicalRecord(id, patient, null, diagnostic, plan, status, time);
    }
}
