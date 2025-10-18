/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.service.AuthService;
import com.ptit.dental.utils.Injector;
import com.ptit.dental.view.LoginView;

/**
 *
 * @author Administrator
 */
public class LoginController extends BaseController<LoginView> {
    public LoginController(LoginView view) {
        super(view);

        view.btnLogin.addActionListener(e -> {

            AuthService authService = Injector.get(AuthService.class);

            String user = view.txtUser.getText();
            System.out.println("Hello, " + user);

            try {
                authService.Login(user, "12345");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });
    }
}