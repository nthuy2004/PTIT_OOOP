///*
// * PTIT OOP
// * QUAN LY PHONG KHAM RANG
// */
package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.service.AuthService;
import com.ptit.dental.utils.Injector;
import com.ptit.dental.view.LoginView;

import javax.swing.*;

public class LoginController extends BaseController<LoginView> {

    private Runnable onLoginSuccess;

    public LoginController(LoginView view) {
        super(view);
        initController();
    }

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    private void initController() {
        view.loginButton.addActionListener(e -> handleLogin());
        view.showPasswordCheck.addActionListener(
                e -> view.passwordField.setEchoChar(view.showPasswordCheck.isSelected() ? (char) 0 : '•'));
    }

    private void handleLogin() {
        String username = view.usernameField.getText().trim();
        String password = new String(view.passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng điền đầy đủ tên đăng nhập và mật khẩu!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            AuthService authService = Injector.get(AuthService.class);
            boolean loginSuccess = authService.Login(username, password);

            if (loginSuccess) {
                JOptionPane.showMessageDialog(view,
                        "Đăng nhập thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                view.dispose();

                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            if (errorMessage == null || errorMessage.isEmpty()) {
                errorMessage = "Đã xảy ra lỗi không xác định khi đăng nhập";
            }

            ex.printStackTrace();

            JOptionPane.showMessageDialog(view,
                    errorMessage,
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
