package com.teamtreehouse.data.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity //map through this obj's data to pass to db
public class Country {
    @Id
    //used for generating new IDs
    @Column(length = 3, nullable = false)
    private String code;

    @Column(length = 32)
    private String name;

    @Column(precision = 11, scale = 8)
    private BigDecimal internetUser;

    @Column(precision = 11, scale = 8)
    private BigDecimal adultLiteracyRate;

    public Country(){};

    public Country(CountryBuilder builder){
        this.code = builder.code;
        this.name = builder.name;
        this.internetUser = builder.internetUser;
        this.adultLiteracyRate = builder.adultLiteracyRate;
    };


    @Override
    public String toString() {
        return String.format("Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", internetUser=" + internetUser +
                ", adultLiteracyRate=" + adultLiteracyRate +
                '}');
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

    public BigDecimal getInternetUser() {
        return internetUser;
    }

    public void setInternetUser(BigDecimal internetUser) {
        this.internetUser = internetUser;
    }

    public BigDecimal getAdultLiteracyRate() {
        return adultLiteracyRate;
    }

    public void setAdultLiteracyRate(BigDecimal adultLiteracyRate) {
        this.adultLiteracyRate = adultLiteracyRate;
    }

    public static class CountryBuilder{
        private String code;
        private String name;
        private BigDecimal internetUser;
        private BigDecimal adultLiteracyRate;

        public CountryBuilder(String code, String name){
            this.code = code;
            this.name = name;
        }

        public CountryBuilder withInternetUsers(BigDecimal internetUsers){
            this.internetUser = internetUsers;
           // System.out.println("Setting internetUsers to: " + internetUsers); // Log when it's set
            return this;
        };

        public CountryBuilder withAdultLiteracyRate(BigDecimal adultLiteracyRate){
            this.adultLiteracyRate = adultLiteracyRate;
            //System.out.println("Setting adultLiteracyRate to: " + adultLiteracyRate); // Log when it's set
            return this;
        };

        //method to create and return the Country object
        public Country build(){
            return new Country(this);
        }
    }
}
