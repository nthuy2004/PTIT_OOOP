/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptit.dental.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class Database {
    public static Database _instance = null;
    private Connection connection;
    
    // Cấu hình database - có thể thay đổi theo môi trường
    private final String URL = "jdbc:mysql://180.93.139.29:23306/dental";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    
    private Database() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Đang kết nối đến database: " + URL);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Kết nối database thành công!");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL Driver not found. Vui lòng kiểm tra thư viện MySQL JDBC.", ex);
        } catch (SQLException ex) {
            System.err.println("❌ Lỗi kết nối database: " + ex.getMessage());
            System.err.println("URL: " + URL);
            System.err.println("Username: " + USERNAME);
            throw ex;
        }
    }
    
    public static Database getInstance() throws SQLException {
        if(_instance == null)
        {
            _instance = new Database();
        }
        return _instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
}
