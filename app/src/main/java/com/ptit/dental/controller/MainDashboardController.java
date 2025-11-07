package com.ptit.dental.controller;

import com.ptit.dental.view.MainDashboardView;
import javax.swing.*;
import java.awt.event.*;

public class MainDashboardController {

    private MainDashboardView view;

    public MainDashboardController(MainDashboardView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Gán hành động cho nút đăng xuất
        view.btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        view,
                        "Bạn có chắc chắn muốn đăng xuất?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    view.dispose(); // đóng cửa sổ
                    // TODO: mở lại màn hình đăng nhập
                }
            }
        });

        // Sự kiện click cho menu đã được xử lý trong MainDashboardView
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainDashboardView view = new MainDashboardView();
            new MainDashboardController(view);
        });
    }

    public void show() {

    }
}
