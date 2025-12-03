package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.view.MainDashboardView;
import javax.swing.*;
import java.awt.event.*;

public class MainDashboardController extends BaseController<MainDashboardView> {


    public MainDashboardController(MainDashboardView view) {
        super(view);
        initController();
    }

    private void initController() {
        view.btnLogout.addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Bạn có chắc chắn muốn đăng xuất?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose();
                
                //add nav to login
            }
        });
        
        
    }
}
