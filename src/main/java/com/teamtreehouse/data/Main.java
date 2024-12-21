package com.teamtreehouse.data;

import com.teamtreehouse.data.model.Country;
import com.teamtreehouse.data.model.Country.CountryBuilder;
import com.teamtreehouse.data.model.Util;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Main {


    public static void main(String[] args) {

        //building country objects
        addSampleCountries();

        List<Country> countries = fetchAllCountries();
        System.out.printf("before: %s%n", countries);

        //trying to grab the updated list of countries
        countries = fetchAllCountries();
        System.out.printf("updated countries: %s%n", countries);

        displayCountryData(countries);

        displayAnalysis(countries);

    }

    private static void addSampleCountries(){
        saveCountry(new CountryBuilder("USA", "United States")
                .withInternetUsers(new BigDecimal("123.12345678"))
                .withAdultLiteracyRate(new BigDecimal("98.5736"))
                .build());

        saveCountry(new CountryBuilder("CAN", "Canada")
                .withInternetUsers(new BigDecimal("98.23456789"))
                .withAdultLiteracyRate(new BigDecimal("99.8765"))
                .build());

        saveCountry(new CountryBuilder("MEX", "Mexico")
                .withInternetUsers(new BigDecimal("75.34567890"))
                .withAdultLiteracyRate(new BigDecimal("94.5678"))
                .build());
    }

    //passing in sample Country with data
    public static void saveCountry(Country country){
        Session session = Util.getSession();
        session.beginTransaction();
        session.save(country);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Country> fetchAllCountries() {
        //sessionFactory, managing Hibernate's creation and handling connection to db
        //session -> interact with db
        Session session = Util.getSession();

        CriteriaQuery<Country> criteriaQuery = session.getCriteriaBuilder().createQuery(Country.class);

        criteriaQuery.from(Country.class);

        List<Country> countries = session.createQuery(criteriaQuery).getResultList();
        System.out.println("Fetched Countries: " + countries.size());

        if (!countries.isEmpty()) {
            countries.forEach(country -> System.out.println(country.toString()));
        } else {
            System.out.println("No countries found.");
        }

        session.close();

        return countries;
    }


    public static void displayCountryData(List<Country> countries){
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("                                            Country's Data              %n");
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

        System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", "CODE", "NAME", "INTERNET USERS", "ADULT LITERACY RATE");
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");


        for(Country country : countries){
            //grab the data you need
            String code = country.getCode();
            String name = country.getName();
            String internetUsers = (country.getInternetUsers().compareTo(BigDecimal.ZERO) == 0) ? "--" : String.format("%.8f", country.getInternetUsers());
            String adultLiteracyRate = (country.getAdultLiteracyRate().compareTo(BigDecimal.ZERO) == 0) ? "--" : String.format("%.8f", country.getAdultLiteracyRate());
            System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", code, name, internetUsers, adultLiteracyRate);
        }
    }

public static void displayAnalysis(List<Country> countries){
    System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");
    System.out.printf("                                       Statistics for Each Country              %n");
    System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

    System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", "CODE", "NAME", "INTERNET USERS", "ADULT LITERACY RATE");
    System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

    //max and min for IU and ALR
    BigDecimal maxInternetUsers = BigDecimal.ZERO; //(0.0)
    BigDecimal minInternetUsers = BigDecimal.valueOf(Double.MAX_VALUE);

    BigDecimal maxAdultLiteracyRate = BigDecimal.ZERO;
    BigDecimal minAdultLiteracyRate = BigDecimal.valueOf(Double.MAX_VALUE);


    for(Country country : countries){

        //grab the data you need
        BigDecimal internetUsers = country.getInternetUsers();
        BigDecimal adultLiteracyRate = country.getAdultLiteracyRate();

        System.out.printf("AdultLiteracyRate: %s", adultLiteracyRate);


        if(internetUsers.compareTo(maxInternetUsers) > 0){
            maxInternetUsers = internetUsers;
        }
        if(internetUsers.compareTo(minInternetUsers) < 0){
            minInternetUsers = internetUsers;
        }

        if(adultLiteracyRate.compareTo(maxAdultLiteracyRate) > 0){
            maxAdultLiteracyRate = adultLiteracyRate;
        }
        if(adultLiteracyRate.compareTo(minAdultLiteracyRate) < 0){
            minAdultLiteracyRate = adultLiteracyRate;
        }

        String code = country.getCode();
        String name = country.getName();
        String updatedInternetUsers = (internetUsers.compareTo(BigDecimal.ZERO) == 0) ? "--" : String.format("%.8f", internetUsers);
        String updatedAdultLiteracyRate = (adultLiteracyRate.compareTo(BigDecimal.ZERO) == 0) ? "--" : String.format("%.8f", adultLiteracyRate);

        System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", code, name, updatedInternetUsers, updatedAdultLiteracyRate);
    }

    System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");
    System.out.printf("| %-20s | %-20s | %-20s | %-20s | %n", "MAX INTERNET USERS", "MIN INTERNET USERS", "MAX ADULT LITERACY RATE", "MIN ADULT LITERACY RATE");
    System.out.printf("| %-20s | %-20s | %-20s | %-20S | %n", maxInternetUsers, minInternetUsers, maxAdultLiteracyRate, minAdultLiteracyRate);
    System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

    };

};