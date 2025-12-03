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
    
    private final String URL = "jdbc:mysql://180.93.139.29:23306/dental";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    
    private Database() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database init ok");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL Driver not found", ex);
        } catch (SQLException ex) {
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
