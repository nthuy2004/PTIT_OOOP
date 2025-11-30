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
    private StaffDAO staffDAO;

    public MedicalRecordDAO(Connection conn) {
        this.conn = conn;
        this.patientDAO = new PatientDAO(conn);
        this.staffDAO = new StaffDAO(conn);
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
        // Tạo prescription mặc định nếu chưa có
        int prescriptionId = getOrCreatePrescription(record.getPatient().getId());
        
        // Lấy service_id đầu tiên (hoặc service mặc định)
        int serviceId = getDefaultServiceId();
        
        // Lấy doctor_id hợp lệ (nếu record không có doctor, lấy doctor mặc định)
        int doctorId = record.getDoctor() != null ? record.getDoctor().getId() : getDefaultDoctorId();
        
        String sql = "INSERT INTO medical_records (patient_id, doctor_id, service_id, prescription_id, diagnostic, plan, status, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, record.getPatient().getId());
            ps.setInt(2, doctorId); // Dùng doctor_id hợp lệ
            ps.setInt(3, serviceId);
            ps.setInt(4, prescriptionId);
            ps.setString(5, record.getDiagnostic());
            ps.setString(6, record.getPlan());
            ps.setString(7, record.getStatus());
            ps.setObject(8, record.getTime());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    record.id = rs.getInt(1);
                }
            }
        }
    }

    public boolean update(MedicalRecord record) throws SQLException {
        // Lấy doctor_id hợp lệ
        int doctorId = record.getDoctor() != null ? record.getDoctor().getId() : getDefaultDoctorId();
        
        String sql = "UPDATE medical_records SET patient_id = ?, doctor_id = ?, service_id = ?, prescription_id = ?, diagnostic = ?, plan = ?, status = ?, time = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, record.getPatient().getId());
            ps.setInt(2, doctorId); // Dùng doctor_id hợp lệ
            // Lấy service_id và prescription_id hiện tại nếu chưa có
            String getCurrentSql = "SELECT service_id, prescription_id FROM medical_records WHERE id = ?";
            int serviceId = getDefaultServiceId();
            int prescriptionId = getOrCreatePrescription(record.getPatient().getId());
            try (PreparedStatement getPs = conn.prepareStatement(getCurrentSql)) {
                getPs.setInt(1, record.getId());
                try (ResultSet rs = getPs.executeQuery()) {
                    if (rs.next()) {
                        serviceId = rs.getInt("service_id");
                        prescriptionId = rs.getInt("prescription_id");
                    }
                }
            }
            ps.setInt(3, serviceId);
            ps.setInt(4, prescriptionId);
            ps.setString(5, record.getDiagnostic());
            ps.setString(6, record.getPlan());
            ps.setString(7, record.getStatus());
            ps.setObject(8, record.getTime());
            ps.setInt(9, record.getId());

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
    
    /**
     * Tạo hoặc lấy prescription cho patient
     */
    private int getOrCreatePrescription(int patientId) throws SQLException {
        // Tìm prescription gần nhất của patient
        String findSql = "SELECT id FROM prescriptions WHERE patient_id = ? ORDER BY id DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(findSql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        
        // Nếu không có, tạo prescription mới
        String insertSql = "INSERT INTO prescriptions (patient_id, status) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, patientId);
            ps.setInt(2, 0); // status = 0 (chưa hoàn thành)
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Không thể tạo prescription");
    }
    
    /**
     * Lấy service_id mặc định (service đầu tiên)
     */
    private int getDefaultServiceId() throws SQLException {
        String sql = "SELECT id FROM services ORDER BY id ASC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        // Nếu không có service nào, throw exception
        throw new SQLException("Không có service nào trong database. Vui lòng thêm ít nhất một service.");
    }
    
    /**
     * Lấy doctor_id mặc định (staff đầu tiên, ưu tiên role = 2 là BACSI)
     */
    private int getDefaultDoctorId() throws SQLException {
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
}
