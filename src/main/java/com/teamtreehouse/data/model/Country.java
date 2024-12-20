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

    //builder constructor
    public Country(CountryBuilder builder){
        this.code = builder.code;
        this.name = builder.name;
        this.internetUsers = builder.internetUsers;
        this.adultLiteracyRate = builder.adultLiteracyRate;
    };


    @Override
    public String toString() {
        return String.format("Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", internetUser=" + internetUsers +
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

    //CountryBuilder CLASS
    public static class CountryBuilder{
        private String code;
        private String name;
        private int internetUsers;
        private int adultLiteracyRate;

        //CountryBuilder CONSTRUCTOR
        //necessary fields needed when creating a country
        public CountryBuilder(String code, String name){
            this.code = code;
            this.name = name;
        }

        public CountryBuilder withInternetUsers(int internetUsers){
            this.internetUsers = internetUsers;
           // System.out.println("Setting internetUsers to: " + internetUsers); // Log when it's set
            return this;
        };

        public CountryBuilder withAdultLiteracyRate(int adultLiteracyRate){
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
