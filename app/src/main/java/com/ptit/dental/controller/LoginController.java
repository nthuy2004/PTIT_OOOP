///*
// * PTIT OOP
// * QUAN LY PHONG KHAM RANG
// */
//package com.ptit.dental.controller;
//
//import com.ptit.dental.base.BaseController;
//import com.ptit.dental.model.service.AuthService;
//import com.ptit.dental.utils.Injector;
//import com.ptit.dental.view.LoginView;
//
///**
// *
// * @author Administrator
// */
//public class LoginController extends BaseController<LoginView> {
//    public LoginController(LoginView view) {
//        super(view);
//
//        view.btnLogin.addActionListener(e -> {
//
//            AuthService authService = Injector.get(AuthService.class);
//
//            String user = view.txtUser.getText();
//            System.out.println("Hello, " + user);
//
//            try {
//                authService.Login(user, "12345");
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//
//        });
//    }
//}

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


//package com.ptit.dental.controller;
//
//import com.ptit.dental.base.BaseController;
//import com.ptit.dental.view.LoginView;
//
//import javax.swing.*;
//
//public class LoginController extends BaseController<LoginView> {
//
//    public LoginController(LoginView view) {
//        super(view);
//        initController();
//    }
//
//
//    private void initController() {
//        view.loginButton.addActionListener(e -> handleLogin());
//        view.showPasswordCheck.addActionListener(e ->
//                view.passwordField.setEchoChar(view.showPasswordCheck.isSelected() ? (char) 0 : '•'));
//    }
//
//    private void handleLogin() {
//        String username = view.usernameField.getText().trim();
//        String password = new String(view.passwordField.getPassword()).trim();
//
//        if (username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(view, "Hãy điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        if (username.equalsIgnoreCase("admin") && password.equals("1")) {
//            JOptionPane.showMessageDialog(view, "Đăng nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            JOptionPane.showMessageDialog(view, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}
//


package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.service.AuthService;
import com.ptit.dental.utils.Injector;
import com.ptit.dental.view.LoginView;

import javax.swing.*;

public class LoginController extends BaseController<LoginView> {

    // ⚡ Callback sẽ chạy sau khi đăng nhập thành công
    private Runnable onLoginSuccess;

    public LoginController(LoginView view) {
        super(view);
        initController();
    }

    // Cho phép App.java gán callback
    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
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
            JOptionPane.showMessageDialog(view, 
                "Vui lòng điền đầy đủ tên đăng nhập và mật khẩu!", 
                "Cảnh báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Lấy AuthService từ Injector
            AuthService authService = Injector.get(AuthService.class);
            
            if (authService == null) {
                JOptionPane.showMessageDialog(view, 
                    "Lỗi: Hệ thống chưa sẵn sàng!\n\n" +
                    "Có thể do:\n" +
                    "- Database chưa được khởi tạo\n" +
                    "- Kết nối database thất bại\n" +
                    "- Vui lòng kiểm tra lại và thử lại sau vài giây", 
                    "Lỗi hệ thống", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Xác thực với database (sẽ throw exception nếu thất bại)
            boolean loginSuccess = authService.Login(username, password);
            
            if (loginSuccess) {
                // Nếu đến đây nghĩa là đăng nhập thành công
                JOptionPane.showMessageDialog(view, 
                    "Đăng nhập thành công!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);

                // Đóng cửa sổ đăng nhập
                view.dispose();

                // ✅ Khi đăng nhập thành công → Gọi callback
                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
            }
        } catch (Exception ex) {
            // Hiển thị thông báo lỗi từ AuthService
            String errorMessage = ex.getMessage();
            if (errorMessage == null || errorMessage.isEmpty()) {
                errorMessage = "Đã xảy ra lỗi không xác định khi đăng nhập";
            }
            
            // In ra console để debug
            System.err.println("Login error: " + errorMessage);
            ex.printStackTrace();
            
            JOptionPane.showMessageDialog(view, 
                "Đăng nhập thất bại!\n\n" + errorMessage + 
                "\n\nVui lòng kiểm tra:\n" +
                "- Tên đăng nhập có đúng không?\n" +
                "- Mật khẩu có đúng không?\n" +
                "- Database đã được kết nối chưa?", 
                "Lỗi đăng nhập", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
