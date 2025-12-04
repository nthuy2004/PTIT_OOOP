/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

/**
 *
 * @author Administrator
 */
public class Service {
    private Integer id;
    private String name;
    private String desc;
    private Double price;

    public Service(Integer id, String name, String desc, Double price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
}