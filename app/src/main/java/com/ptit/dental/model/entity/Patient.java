/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.entity;

import com.ptit.dental.model.enums.Gender;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Patient {
    private int id;
    private String fullname;
    private Date birthday;
    private Gender gender;
    private String address;
    private String phone;

    public Patient() {
    }

    public Patient(int id, String fullname, Date birthday, Gender gender, String address, String phone) {
        this.id = id;
        this.fullname = fullname;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

