package com.teamtreehouse.data.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Util {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    //creates and configures the connection to the db
    private static SessionFactory buildSessionFactory() {

        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }

}
