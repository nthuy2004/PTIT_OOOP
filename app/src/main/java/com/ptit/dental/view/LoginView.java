///*
// * PTIT OOP
// * QUAN LY PHONG KHAM RANG
// */
//package com.ptit.dental.view;
//
//import com.ptit.dental.base.BaseView;
//
///**
// *
// * @author Administrator
// */
//import javax.swing.*;
//import java.awt.*;
//
//public class LoginView extends BaseView {
//    public JTextField txtUser = new JTextField(10);
//    public JButton btnLogin = new JButton("Login");
//
//    public LoginView() {
//        super("Login");
//        setLayout(new FlowLayout());
//        add(new JLabel("Username:"));
//        add(txtUser);
//        add(btnLogin);
//    }
//}

package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;
import javax.swing.*;
import java.awt.*;

public class LoginView extends BaseView {

    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton loginButton;
    public JCheckBox showPasswordCheck;

    public LoginView() {
        super("PHÒNG KHÁM RĂNG");
        initUI();
    }

    private void initUI() {
        setSize(650, 450);
        setLocationRelativeTo(null);

        String imagePath = "/com/ptit/dental/image/";
        // Ảnh nền
//
        Image bgImage = new ImageIcon(getClass().getResource(imagePath + "Blue and White Modern Dental Medical Presentation.png")).getImage();

        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 10, 7, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel logoLabel = new JLabel("QUẢN LÝ PHÒNG KHÁM RĂNG", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        logoLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(logoLabel, gbc);

        // Username label
        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel usernameLabel = new JLabel("Tên Người Dùng", SwingConstants.RIGHT);
        usernameLabel.setForeground(Color.WHITE);
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        // Password label
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel passwordLabel = new JLabel("Mật Khẩu", SwingConstants.RIGHT);
        passwordLabel.setForeground(Color.WHITE);
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        // Show password
        gbc.gridy++;
        gbc.gridx = 1;
        showPasswordCheck = new JCheckBox("Hiển thị mật khẩu");
        showPasswordCheck.setOpaque(false);
        showPasswordCheck.setForeground(Color.WHITE);
        formPanel.add(showPasswordCheck, gbc);

        // Login button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        loginButton = new JButton("Đăng Nhập");
        formPanel.add(loginButton, gbc);

        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);
    }
}
