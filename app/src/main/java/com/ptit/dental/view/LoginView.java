/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.view;

import com.ptit.dental.base.BaseView;

/**
 *
 * @author Administrator
 */
import javax.swing.*;
import java.awt.*;

public class LoginView extends BaseView {
    public JTextField txtUser = new JTextField(10);
    public JButton btnLogin = new JButton("Login");

    public LoginView() {
        super("Login");
        setLayout(new FlowLayout());
        add(new JLabel("Username:"));
        add(txtUser);
        add(btnLogin);
    }
}