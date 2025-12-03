/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.service;

import com.ptit.dental.model.dao.StaffDAO;
import com.ptit.dental.model.entity.Staff;
import com.ptit.dental.utils.Util;

import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class AuthService {
    private StaffDAO staffDAO;

    public static Staff staffInfo;

    public AuthService(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    public boolean Login(String username, String password) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new Exception("Tên đăng nhập không được để trống");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new Exception("Mật khẩu không được để trống");
        }
        
        try {
            Staff s = staffDAO.getByUsername(username.trim());
            if(s == null){
                throw new Exception("Tên đăng nhập không tồn tại");
            }
            
            String inputPasswordHash = Util.md5(password.trim());
            String storedPasswordHash = s.getPassword();
            
            if(!storedPasswordHash.equals(inputPasswordHash)){
                throw new Exception("Mật khẩu không chính xác");
            }
            
            staffInfo = s;
            return true;
        } catch (SQLException e) {
            System.err.println("Database error during login: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    
    public Staff getCurrentStaff() {
        return staffInfo;
    }
}
