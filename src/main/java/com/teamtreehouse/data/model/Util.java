package com.teamtreehouse.data.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Util {

    //allows java code to interact with db; reusable reference to a SessionFactory (buildSessionFactory)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    //creates and configures the connection to the db
    //session field is initialized, according to our code, before calling the main method
    private static SessionFactory buildSessionFactory() {

        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }

}
