package com.teamtreehouse.data.model;

import jakarta.persistence.*;

@Entity //map through this obj's data to pass to db
public class Country {
    @Id
    //used for generating new IDs
    @Column(length = 3, nullable = false)
    private String code;

    @Column(length = 32)
    private String name;

    @Column(length = 11, precision = 8)
    private int internetUsers;

    @Column(length = 11, precision = 8)
    private int adultLiteracyRate;

    //Default constructor for JPA
    //JPA will call our default constructor when instantiating Country obj
    //we need to make sure one is present
    public Country(){};

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", internetUser=" + internetUsers +
                ", adultLiteracyRate=" + adultLiteracyRate +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInternetUsers() {
        return internetUsers;
    }

    public void setInternetUsers(int internetUsers) {
        this.internetUsers = internetUsers;
    }

    public int getAdultLiteracyRate() {
        return adultLiteracyRate;
    }

    public void setAdultLiteracyRate(int adultLiteracyRate) {
        this.adultLiteracyRate = adultLiteracyRate;
    }
}
