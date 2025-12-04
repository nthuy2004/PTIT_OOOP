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
    private Integer id;
    private String drugName;
    private Integer quantity;
    private String usage;

    public PrescriptionDetail(Integer id, String drugName, Integer quantity, String usage) {
        this.id = id;
        this.drugName = drugName;
        this.quantity = quantity;
        this.usage = usage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
    
    
}