/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.dao;

import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.model.enums.Gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class PatientDAO {

    private Connection conn;

    public PatientDAO(Connection conn) {
        this.conn = conn;
    }

    public Patient getById(int id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                            rs.getInt("id"),
                            rs.getString("fullname"),
                            rs.getDate("birthday"),
                            Gender.fromInt(rs.getInt("gender")),
                            rs.getString("address"),
                            rs.getString("phone"));
                }
            }
        }
        return null;
    }

    public List<Patient> getAll() throws SQLException {
        String sql = "SELECT * FROM patients ORDER BY id DESC";
        List<Patient> patientList = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getDate("birthday"),
                        Gender.fromInt(rs.getInt("gender")),
                        rs.getString("address"),
                        rs.getString("phone"));
                patientList.add(p);
            }
        }
        return patientList;
    }

    public List<Patient> searchByName(String keyword) throws SQLException {
        String sql = "SELECT * FROM patients WHERE fullname LIKE ? ORDER BY id DESC";
        List<Patient> patientList = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Patient p = new Patient(
                            rs.getInt("id"),
                            rs.getString("fullname"),
                            rs.getDate("birthday"),
                            Gender.fromInt(rs.getInt("gender")),
                            rs.getString("address"),
                            rs.getString("phone"));
                    patientList.add(p);
                }
            }
        }
        return patientList;
    }

    public void insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (fullname, birthday, gender, address, phone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getFullname());
            ps.setDate(2, new java.sql.Date(patient.getBirthday().getTime()));
            ps.setInt(3, patient.getGender().getValue());
            ps.setString(4, patient.getAddress());
            ps.setString(5, patient.getPhone());
            ps.executeUpdate();
        }
    }

    public boolean update(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET fullname = ?, birthday = ?, gender = ?, address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getFullname());
            ps.setDate(2, new java.sql.Date(patient.getBirthday().getTime()));
            ps.setInt(3, patient.getGender().getValue());
            ps.setString(4, patient.getAddress());
            ps.setString(5, patient.getPhone());
            ps.setInt(6, patient.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
