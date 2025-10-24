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

    private Staff staffInfo;

    public AuthService(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    public boolean Login(String username, String password) throws Exception {
        Staff s = staffDAO.getByUsername(username);
        if(s == null){
            throw new Exception("Invalid username or password");
        }
        if(!s.getPassword().equals(Util.md5(password))){
            throw new Exception("Invalid password");
        }
        return true;
    }
}
