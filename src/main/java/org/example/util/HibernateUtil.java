package org.example.util;

import org.example.model.Client;
import org.example.model.Planet;
import org.example.model.Ticket;
import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;
import java.util.TimeZone;

public class HibernateUtil {
    private static final HibernateUtil INSTANCE;

    private SessionFactory sessionFactory;

    static {
        INSTANCE = new HibernateUtil();
    }

    private HibernateUtil() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(Client.class);
        cfg.addAnnotatedClass(Planet.class);
        cfg.addAnnotatedClass(Ticket.class);

        sessionFactory = cfg.buildSessionFactory();
    }

    public static HibernateUtil getInstance() {
        return INSTANCE;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session openSession() {
        return INSTANCE.sessionFactory.openSession();
    }

    public static void close() {
        INSTANCE.sessionFactory.close();
    }


}
