/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.base;

/**
 *
 * @author Administrator
 */
public class BaseController<E extends BaseView> {
    protected E view;

    public BaseController(E view) {
        this.view = view;
    }

    public void show() {
        view.showView();
    }
}