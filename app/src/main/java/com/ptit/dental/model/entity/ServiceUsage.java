/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

/**
 *
 * @author Administrator
 */
public class ServiceUsage {
    public Integer id;
    public Service service;
    public Integer quantity;
    public String note;

    public ServiceUsage(Integer id, Service service, Integer quantity, String note) {
        this.id = id;
        this.service = service;
        this.quantity = quantity;
        this.note = note;
    }
}