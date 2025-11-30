/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.dao;

import com.ptit.dental.model.entity.Staff;
import com.ptit.dental.model.enums.Gender;
import com.ptit.dental.model.enums.Role;

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
public class StaffDAO {

    private Connection conn;

    public StaffDAO(Connection conn) {
        this.conn = conn;
    }

    public Staff getById(int id) throws SQLException {
        String sql = "SELECT * FROM staff WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("fullname"),
                            rs.getDate("birthday"),
                            Gender.fromInt(rs.getInt("gender")),
                            rs.getString("address"),
                            rs.getString("phone"),
                            Role.fromInt(rs.getInt("role"))
                    );
                }
            }
        }
        return null;
    }

    public Staff getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM staff WHERE LOWER(username) = LOWER(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Trim password để loại bỏ whitespace có thể có trong database
                    String password = rs.getString("password");
                    if (password != null) {
                        password = password.trim();
                    }
                    
                    return new Staff(
                            rs.getInt("id"),
                            rs.getString("username"),
                            password,
                            rs.getString("email"),
                            rs.getString("fullname"),
                            rs.getDate("birthday"),
                            Gender.fromInt(rs.getInt("gender")),
                            rs.getString("address"),
                            rs.getString("phone"),
                            Role.fromInt(rs.getInt("role"))
                    );
                }
            }
        }
        return null;
    }

    public List<Staff> getAll() throws SQLException {
        String sql = "SELECT * FROM staff";
        List<Staff> staffList = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Staff s = new Staff(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("fullname"),
                        rs.getDate("birthday"),
                        Gender.fromInt(rs.getInt("gender")),
                        rs.getString("address"),
                        rs.getString("phone"),
                        Role.fromInt(rs.getInt("role"))
                );
                staffList.add(s);
            }
        }
        return staffList;
    }
}
