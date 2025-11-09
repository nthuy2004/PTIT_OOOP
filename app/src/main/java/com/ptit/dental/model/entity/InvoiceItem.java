/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

/**
 *
 * @author Administrator
 */
public class InvoiceItem {
    private String serviceName;
    private double unitPrice;
    private int quantity;
    private double total;

    public InvoiceItem() {
    }

    public InvoiceItem(String serviceName, double unitPrice, int quantity, double total) {
        this.serviceName = serviceName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
    }

    // Getter và Setter
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "serviceName='" + serviceName + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}

