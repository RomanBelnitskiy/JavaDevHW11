package org.example.util;

import org.example.model.Client;
import org.example.model.Planet;
import org.example.model.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtil {
    private static final HibernateUtil INSTANCE;

    private final SessionFactory sessionFactory;

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


    public static void inSession(Consumer<Session> work) {
        var session = getInstance().getSessionFactory().openSession();
        var transaction = session.getTransaction();
        try {
            transaction.begin();
            work.accept(session);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }

    public static <R> List<R> inSessionReturnList(Function<Session, List<R>> work) {
        var session = getInstance().getSessionFactory().openSession();
        var transaction = session.getTransaction();
        try {
            transaction.begin();
            List<R> result = work.apply(session);
            transaction.commit();
            return result;
        }
        catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }

    public static <R> R inSessionReturnEntity(Function<Session, R> work) {
        var session = getInstance().getSessionFactory().openSession();
        var transaction = session.getTransaction();
        try {
            transaction.begin();
            R result = work.apply(session);
            transaction.commit();
            return result;
        }
        catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }
}
