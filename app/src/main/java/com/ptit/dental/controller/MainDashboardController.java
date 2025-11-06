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

        // Gắn sự kiện click cho từng panel chức năng
        for (java.awt.Component comp : view.contentPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel label = (JLabel) ((JPanel) panel).getComponent(1);
                        String title = label.getText();
                        JOptionPane.showMessageDialog(view, "Mở chức năng: " + title);
                    }
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainDashboardView view = new MainDashboardView();
            new MainDashboardController(view);
        });
    }
}
