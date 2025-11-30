package com.ptit.dental.model.dao;

/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */

import com.ptit.dental.model.entity.Drug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DrugDAO {

    private Connection conn;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DrugDAO(Connection conn) {
        this.conn = conn;
    }

    public Drug getById(int id) throws SQLException {
        String sql = "SELECT * FROM drugs WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Drug m = new Drug();
                    m.setId(rs.getInt("id"));
                    m.setName(rs.getString("name"));
                    m.setPrice(rs.getDouble("price"));
                    m.setQuantity(rs.getInt("quantity"));

                    // Parse importDate and expiryDate from desc field (format: "importDate;expiryDate")
                    String desc = rs.getString("desc");
                    if (desc != null && !desc.isEmpty() && desc.contains(";")) {
                        String[] dates = desc.split(";");
                        if (dates.length >= 2) {
                            try {
                                m.setImportDate(dateFormat.parse(dates[0]));
                                m.setExpiryDate(dateFormat.parse(dates[1]));
                            } catch (Exception e) {
                                // If parsing fails, set to null
                                m.setImportDate(new Date());
                                m.setExpiryDate(new Date());
                            }
                        } else {
                            m.setImportDate(new Date());
                            m.setExpiryDate(new Date());
                        }
                    } else {
                        m.setImportDate(new Date());
                        m.setExpiryDate(new Date());
                    }

                    return m;
                }
            }
        }
        return null;
    }

    public List<Drug> getAll() throws SQLException {
        String sql = "SELECT * FROM drugs ORDER BY id DESC";
        List<Drug> medicineList = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Drug m = new Drug();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setPrice(rs.getDouble("price"));
                m.setQuantity(rs.getInt("quantity"));

                // Parse importDate and expiryDate from desc field
                String desc = rs.getString("desc");
                if (desc != null && !desc.isEmpty() && desc.contains(";")) {
                    String[] dates = desc.split(";");
                    if (dates.length >= 2) {
                        try {
                            m.setImportDate(dateFormat.parse(dates[0]));
                            m.setExpiryDate(dateFormat.parse(dates[1]));
                        } catch (Exception e) {
                            m.setImportDate(new Date());
                            m.setExpiryDate(new Date());
                        }
                    } else {
                        m.setImportDate(new Date());
                        m.setExpiryDate(new Date());
                    }
                } else {
                    m.setImportDate(new Date());
                    m.setExpiryDate(new Date());
                }

                medicineList.add(m);
            }
        }
        return medicineList;
    }

    public List<Drug> searchByName(String keyword) throws SQLException {
        String sql = "SELECT * FROM drugs WHERE name LIKE ? ORDER BY id DESC";
        List<Drug> medicineList = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Drug m = new Drug();
                    m.setId(rs.getInt("id"));
                    m.setName(rs.getString("name"));
                    m.setPrice(rs.getDouble("price"));
                    m.setQuantity(rs.getInt("quantity"));

                    // Parse importDate and expiryDate from desc field
                    String desc = rs.getString("desc");
                    if (desc != null && !desc.isEmpty() && desc.contains(";")) {
                        String[] dates = desc.split(";");
                        if (dates.length >= 2) {
                            try {
                                m.setImportDate(dateFormat.parse(dates[0]));
                                m.setExpiryDate(dateFormat.parse(dates[1]));
                            } catch (Exception e) {
                                m.setImportDate(new Date());
                                m.setExpiryDate(new Date());
                            }
                        } else {
                            m.setImportDate(new Date());
                            m.setExpiryDate(new Date());
                        }
                    } else {
                        m.setImportDate(new Date());
                        m.setExpiryDate(new Date());
                    }

                    medicineList.add(m);
                }
            }
        }
        return medicineList;
    }

    public void insert(Drug medicine) throws SQLException {
        String sql = "INSERT INTO drugs (name, type, desc, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicine.getName());
            ps.setInt(2, 1); // Default type
            // Store importDate and expiryDate in desc field as "importDate;expiryDate"
            String desc = dateFormat.format(medicine.getImportDate()) + ";" +
                    dateFormat.format(medicine.getExpiryDate());
            ps.setString(3, desc);
            ps.setInt(4, medicine.getQuantity());
            ps.setDouble(5, medicine.getPrice());
            ps.executeUpdate();
        }
    }

    public boolean update(Drug medicine) throws SQLException {
        String sql = "UPDATE drugs SET name = ?, desc = ?, quantity = ?, price = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicine.getName());
            // Store importDate and expiryDate in desc field
            String desc = dateFormat.format(medicine.getImportDate()) + ";" +
                    dateFormat.format(medicine.getExpiryDate());
            ps.setString(2, desc);
            ps.setInt(3, medicine.getQuantity());
            ps.setDouble(4, medicine.getPrice());
            ps.setInt(5, medicine.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM drugs WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}

