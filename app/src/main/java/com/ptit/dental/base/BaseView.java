/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.base;

import javax.swing.*;

/**
 *
 * @author Administrator
 */

public abstract class BaseView extends JFrame {
    public BaseView() {

    }
    public BaseView(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    public void showView() {
        setVisible(true);
    }
}