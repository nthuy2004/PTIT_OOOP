/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

/**
 *
 * @author Administrator
 */
public class PrescriptionDetail {
    public Integer id;
    public String drugName;
    public Integer quantity;
    public String usage;

    public PrescriptionDetail(Integer id, String drugName, Integer quantity, String usage) {
        this.id = id;
        this.drugName = drugName;
        this.quantity = quantity;
        this.usage = usage;
    }
}