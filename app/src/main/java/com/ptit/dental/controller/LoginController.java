/////*
//// * PTIT OOP
//// * QUAN LY PHONG KHAM RANG
//// */
////package com.ptit.dental.controller;
////
////import com.ptit.dental.base.BaseController;
////import com.ptit.dental.model.service.AuthService;
////import com.ptit.dental.utils.Injector;
////import com.ptit.dental.view.LoginView;
////
/////**
//// *
//// * @author Administrator
//// */
////public class LoginController extends BaseController<LoginView> {
////    public LoginController(LoginView view) {
////        super(view);
////
////        view.btnLogin.addActionListener(e -> {
////
////            AuthService authService = Injector.get(AuthService.class);
////
////            String user = view.txtUser.getText();
////            System.out.println("Hello, " + user);
////
////            try {
////                authService.Login(user, "12345");
////            } catch (Exception ex) {
////                throw new RuntimeException(ex);
////            }
////
////        });
////    }
////}
//
//
//package com.ptit.dental.controller;
//
//import com.ptit.dental.model.User;
//
//public class LoginController {
//
//    // Giả lập dữ liệu — trong thực tế bạn sẽ kết nối CSDL tại đây
//    private static final String ADMIN_USERNAME = "admin";
//    private static final String ADMIN_PASSWORD = "1";
//
//    public boolean authenticate(User user) {
//        if (user == null) return false;
//        return ADMIN_USERNAME.equalsIgnoreCase(user.getUsername())
//                && ADMIN_PASSWORD.equals(user.getPassword());
//    }
//}


package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.view.LoginView;

import javax.swing.*;

public class LoginController extends BaseController<LoginView> {

    public LoginController(LoginView view) {
        super(view);
        initController();
    }

    private void initController() {
        view.loginButton.addActionListener(e -> handleLogin());
        view.showPasswordCheck.addActionListener(e ->
                view.passwordField.setEchoChar(view.showPasswordCheck.isSelected() ? (char) 0 : '•'));
    }

    private void handleLogin() {
        String username = view.usernameField.getText().trim();
        String password = new String(view.passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Hãy điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.equalsIgnoreCase("admin") && password.equals("1")) {
            JOptionPane.showMessageDialog(view, "Đăng nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

