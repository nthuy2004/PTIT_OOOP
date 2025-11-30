package com.ptit.dental.model.entity;

/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */


import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Drug {
    private int id;
    private String name;
    private Date importDate;
    private Date expiryDate;
    private double price;
    private int quantity;

    public Drug() {
    }

    public Drug(int id, String name, Date importDate, Date expiryDate, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.importDate = importDate;
        this.expiryDate = expiryDate;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", importDate=" + importDate +
                ", expiryDate=" + expiryDate +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

