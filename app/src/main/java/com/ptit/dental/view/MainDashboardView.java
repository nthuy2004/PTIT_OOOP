

package com.ptit.dental.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.ptit.dental.base.BaseView;

public class MainDashboardView extends BaseView {

    public JButton btnLogout;
    public JPanel contentPanel;

    public MainDashboardView() {
        setTitle("Quản lý răng bệnh nhân - Trang chủ");
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String imagePath = "/com/ptit/dental/image/";

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Logo + tên app
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);

        JLabel logoLabel = new JLabel(new ImageIcon(getClass().getResource(imagePath + "rang1.png")));
        JLabel titleLabel = new JLabel("<html><b>Dental Management System</b><br>Hệ thống quản lý răng</html>");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 17));

        logoPanel.add(logoLabel);
        logoPanel.add(Box.createHorizontalStrut(10));
        logoPanel.add(titleLabel);

        // Nút đăng xuất
        btnLogout = new JButton("Đăng xuất");
        btnLogout.setIcon(new ImageIcon(getClass().getResource(imagePath + "LOGOUT.png")));
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setBorder(null);
        btnLogout.setFont(new Font("SansSerif", Font.BOLD, 14));

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // ===== CONTENT =====
        contentPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        contentPanel.setBackground(new Color(245, 245, 245));

        contentPanel.add(createMenuButton("Nhân sự", imagePath + "employee.png"));

        contentPanel.add(createMenuButton("Thuốc", imagePath + "medicine.png"));

        contentPanel.add(createMenuButton("Bệnh nhân", imagePath + "patientdetals.png"));
        contentPanel.add(createMenuButton("Hóa đơn", imagePath + "invoice.png"));

        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createMenuButton(String title, String iconPath) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true));

        JLabel icon = new JLabel(new ImageIcon(getClass().getResource(iconPath)), JLabel.CENTER);
        JLabel label = new JLabel(title, JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));

        panel.add(icon, BorderLayout.CENTER);
        panel.add(label, BorderLayout.SOUTH);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(240, 240, 240));
            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE);
            }
        });

        return panel;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
