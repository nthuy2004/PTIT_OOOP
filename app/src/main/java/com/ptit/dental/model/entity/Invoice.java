/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Invoice {
    private int id;
    private int patientId;
    private Date createdDate;
    private List<InvoiceItem> items;
    private double subtotal;
    private double tax;
    private double discount;
    private double total;
    private String note;

    public Invoice() {
        this.items = new ArrayList<>();
    }

    public Invoice(int id, int patientId, Date createdDate, List<InvoiceItem> items, 
                   double subtotal, double tax, double discount, double total, String note) {
        this.id = id;
        this.patientId = patientId;
        this.createdDate = createdDate;
        this.items = items != null ? items : new ArrayList<>();
        this.subtotal = subtotal;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
        this.note = note;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", createdDate=" + createdDate +
                ", items=" + items +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", discount=" + discount +
                ", total=" + total +
                ", note='" + note + '\'' +
                '}';
    }


}

