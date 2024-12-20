package com.teamtreehouse.data;

import com.teamtreehouse.data.model.Country;
import com.teamtreehouse.data.model.Country.CountryBuilder;
import com.teamtreehouse.data.model.Util;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import java.util.List;


public class Main {


    public static void main(String[] args) {
        //retrieve list of countries
        List<Country> countries = fetchAllCountries();

        //building country obj
        Country country = new CountryBuilder("USA", "United States")
                .withInternetUsers(89)
                .withAdultLiteracyRate(98)
                .build();

        //persist the new country obj to db
        saveCountry(country);

        countries = fetchAllCountries();


        //display list of contacts BEFORE THE UPDATE
        System.out.printf("%n%n Before update %n%n");
        fetchAllCountries().forEach(System.out::println);

        System.out.printf("%n%n After update %n%n");
        displayCountryData(countries);
    }

    public static List<Country> fetchAllCountries() {
       //sessionFactory, managing Hibernate's creation and handling connectin to db
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


        //obj that holds code, name, internet user, adult
        for(Country country : countries){
            //grab the data you need
            String code = country.getCode();
            String name = country.getName();
            String internetUsers = (country.getInternetUsers() == 0) ? "--" : String.format("%.2f", country.getInternetUsers());
            String adultLiteracyRate = (country.getAdultLiteracyRate() == 0) ? "--" : String.format("%.2f", country.getAdultLiteracyRate());
            System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", code, name, internetUsers, adultLiteracyRate);
        }
    }

    //passing in sample Country with data
    public static void saveCountry(Country country){
        Session session = Util.getSession();
        session.beginTransaction();
        session.save(country);
        session.getTransaction().commit();
        session.close();
    }

};