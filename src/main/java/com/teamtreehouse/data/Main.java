package com.teamtreehouse.data;

import com.teamtreehouse.data.model.Country;
import com.teamtreehouse.data.model.Country.CountryBuilder;
import com.teamtreehouse.data.model.Util;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    public void run(){

        List<Country> countries = fetchAllCountries();
        System.out.printf("Countries data: %s", countries);


        Scanner scanner = new Scanner(System.in);

        String choice;
        do {
            displayMenu();
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayCountryData(countries);
                    break;
                case "2":
                    displayAnalysis(countries);
                    break;
                case "3":
                    countries = fetchAllCountries();
                    break;
                case "4":
                    editCountry();
                    countries = fetchAllCountries();
                    break;
                case "5":
                    createNewCountry();
                    countries = fetchAllCountries();
                    break;
                case "6":
                    deleteCountry();
                    countries = fetchAllCountries();
                    break;
                case "7":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (true);
    }

    public void displayMenu(){
        System.out.println("Choose an option:");
        System.out.println("[1] Display List of Countries");
        System.out.println("[2] Display Analysis");
        System.out.println("[3] Refresh Data");
        System.out.println("[4] Edit Country");
        System.out.println("[5] Create New Country");
        System.out.println("[6] Delete a Country");
        System.out.println("[7] Exit");
    }

//    private static void addSampleCountries(){
//        saveCountry(new CountryBuilder("USA", "United States")
//                .withInternetUsers(new BigDecimal("123.12345678"))
//                .withAdultLiteracyRate(new BigDecimal("98.5736"))
//                .build());
//
//        saveCountry(new CountryBuilder("CAN", "Canada")
//                .withInternetUsers(new BigDecimal("98.23456789"))
//                .withAdultLiteracyRate(new BigDecimal("99.8765"))
//                .build());
//
//        saveCountry(new CountryBuilder("MEX", "Mexico")
//                .withInternetUsers(new BigDecimal("75.34567890"))
//                .withAdultLiteracyRate(new BigDecimal("94.5678"))
//                .build());
//    }

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

        session.close();

        return countries;
    }

    public static void displayCountryData(List<Country> countries) {
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("                                            Country's Data              %n");
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

        System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", "CODE", "NAME", "INTERNET USERS", "ADULT LITERACY RATE");
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

        for (Country country : countries) {
            String code = country.getCode();
            String name = country.getName();

            // Check if internetUser is null and handle it accordingly
            BigDecimal internetUser = country.getInternetUser();
            String internetUsers = (internetUser == null || internetUser.compareTo(BigDecimal.ZERO) == 0)
                    ? "--"
                    : String.format("%.2f", internetUser);

            // Check if adultLiteracyRate is null and handle it accordingly
            BigDecimal adultLiteracyRate = country.getAdultLiteracyRate();
            String adultLiteracyRateFormatted = (adultLiteracyRate == null || adultLiteracyRate.compareTo(BigDecimal.ZERO) == 0)
                    ? "--"
                    : String.format("%.2f", adultLiteracyRate);

            System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", code, name, internetUsers, adultLiteracyRateFormatted);
        }
    }


    public static void displayAnalysis(List<Country> countries){
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("                                       Statistics for Each Country              %n");
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

        System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", "CODE", "NAME", "INTERNET USERS", "ADULT LITERACY RATE");
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

        // max and min for IU and ALR
        BigDecimal maxInternetUsers = BigDecimal.ZERO; //(0.0)
        BigDecimal minInternetUsers = BigDecimal.valueOf(100);

        BigDecimal maxAdultLiteracyRate = BigDecimal.ZERO;
        BigDecimal minAdultLiteracyRate = BigDecimal.valueOf(100);

        for(Country country : countries){
            BigDecimal internetUsers = country.getInternetUser();
            BigDecimal adultLiteracyRate = country.getAdultLiteracyRate();

            // Check for null values and update max/min values
            if (internetUsers != null) {
                if (internetUsers.compareTo(maxInternetUsers) > 0) {
                    maxInternetUsers = internetUsers;
                }
                if (internetUsers.compareTo(minInternetUsers) < 0) {
                    minInternetUsers = internetUsers;
                }
            }

            if (adultLiteracyRate != null) {
                if (adultLiteracyRate.compareTo(maxAdultLiteracyRate) > 0) {
                    maxAdultLiteracyRate = adultLiteracyRate;
                }
                if (adultLiteracyRate.compareTo(minAdultLiteracyRate) < 0) {
                    minAdultLiteracyRate = adultLiteracyRate;
                }
            }

            // Formatting output for each country
            String code = country.getCode();
            String name = country.getName();
            String updatedInternetUsers = (internetUsers == null || internetUsers.compareTo(BigDecimal.ZERO) == 0) ? "--" : String.format("%.2f", internetUsers);
            String updatedAdultLiteracyRate = (adultLiteracyRate == null || adultLiteracyRate.compareTo(BigDecimal.ZERO) == 0) ? "--" : String.format("%.2f", adultLiteracyRate);

            System.out.printf("| %-10s | %-32s | %-20s | %-20s |%n", code, name, updatedInternetUsers, updatedAdultLiteracyRate);
        }

        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");
        // Print max and min without checking for null
        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %n",
                "MAX INTERNET USERS", "MIN INTERNET USERS", "MAX ADULT LITERACY RATE", "MIN ADULT LITERACY RATE");
        System.out.printf("| %-20s | %-20s | %-20s | %-20S | %n",
                String.format("%.2f", maxInternetUsers),
                String.format("%.2f", minInternetUsers),
                String.format("%.2f", maxAdultLiteracyRate),
                String.format("%.2f", minAdultLiteracyRate));
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");
    }



    public static Country editCountry(){
        //prompt user for country code to edit
        Scanner scanner = new Scanner(System.in);

        System.out.printf("Enter the country code to edit: ");
        String code = scanner.nextLine().toUpperCase();

        //returning existing data for the given country code
        Session session = Util.getSession();
        Country country = session.get(Country.class, code);

        if(country == null){
            System.out.println("Country with code " + code + " not found.");
            session.close();
            return null;
        }

        System.out.println("Existing Country Data");
        System.out.println("Code : " + country.getName());
        System.out.println("Name : " + country.getName());
        System.out.println("Internet User: " + country.getInternetUser());
        System.out.println("Adult Literacy Rate: " + country.getAdultLiteracyRate());

        System.out.print("Enter the new name(or press Enter to keep current data as is)");
        String newName = scanner.nextLine();
        country.setName(newName);

        System.out.print("Enter new Internet Users (or press Enter to keep current data as is");
        String newIU = scanner.nextLine();
        if (!newIU.trim().isEmpty()) {
            try {
                country.setInternetUser(new BigDecimal(newIU));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for Internet Users. Keeping the current value.");
            }
        }

        System.out.print("Enter new Adult Literacy Rate (or press Enter to keep current data as is");
        String newARL = scanner.nextLine();
        if (!newARL.trim().isEmpty()) {
            try {
                country.setAdultLiteracyRate(new BigDecimal(newARL));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for Literacy Rate. Keeping the current value.");
            }
        }

        session.beginTransaction();
        session.save(country);
        session.getTransaction().commit();
        session.close();

        return null;
    };

    //add new country
    public static Country createNewCountry(){
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("                                       Create Your Country             %n");
        System.out.printf("-------------------------------------------------------------------------------------------------------------------%n");

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Add your Country %n");

        // Prompt user for Country Code and validate input
        System.out.printf("Enter country's code (required): %n");
        String newCountryCode = scanner.nextLine().toUpperCase();
        if (newCountryCode == null || newCountryCode.trim().isEmpty()) {
            newCountryCode = "--"; // Represent null as "--"
        }

        // Prompt user for Country Name and validate input
        System.out.printf("Enter country's name (required): %n");
        String newCountryName = scanner.nextLine();
        if (newCountryName == null || newCountryName.trim().isEmpty()) {
            newCountryName = "--"; // Represent null as "--"
        }

        // Prompt user for Internet Users and validate input
        System.out.printf("Enter Internet Users (optional): %n");
        String internetUserInput = scanner.nextLine();
        BigDecimal newInternetUser = null;
        if (!internetUserInput.trim().isEmpty()) {
            try {
                newInternetUser = new BigDecimal(internetUserInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for Internet Users. Please enter a valid number.");
            }
        }

        // Prompt user for Adult Literacy Rate and validate input
        System.out.printf("Enter Adult Literacy Rate (optional): %n");
        String literacyRateInput = scanner.nextLine();
        BigDecimal newAdultLiteracyRate = null;
        if (!literacyRateInput.trim().isEmpty()) {
            try {
                newAdultLiteracyRate = new BigDecimal(literacyRateInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for Literacy Rate. Please enter a valid number.");
            }
        }

        Country country = new Country.CountryBuilder(newCountryCode, newCountryName)
                .withInternetUsers(newInternetUser)
                .withAdultLiteracyRate(newAdultLiteracyRate)
                .build();

        saveCountry(country);

        // Display values in the correct format
        System.out.printf("Country Created: [Code: %s, Name: %s, Internet Users: %s, Literacy Rate: %s]%n",
                newCountryCode, newCountryName, newInternetUser, newAdultLiteracyRate);

        return country;
    };

    public static void deleteCountry() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the country code of the country you want to delete: ");
        String code = scanner.nextLine();

        Session session = Util.getSession();
        try {
            session.beginTransaction();

            // Fetch the country based on the provided code
            Country country = session.get(Country.class, code);
            if (country == null) {
                System.out.println("Sorry, no country found with code: " + code);
            } else {
                // Confirm deletion with the user
                System.out.print("Are you sure you want to delete " + country.getName() + "? (yes/no): ");
                String confirmation = scanner.nextLine();
                if ("yes".equalsIgnoreCase(confirmation)) {
                    session.delete(country);
                    System.out.println("Country " + country.getName() + " has been deleted successfully.");
                } else {
                    System.out.println("Deletion canceled.");
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback(); // Rollback in case of an error
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            session.close();
        }
    }

};