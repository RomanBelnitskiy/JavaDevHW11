package org.example.service;

import org.example.model.Planet;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public class PlanetService {
    private final Pattern pattern = Pattern.compile("^[A-Z0-9]+$");

    public Planet getById(String id) {
        try (Session session = HibernateUtil.openSession()) {
            return session.get(Planet.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Planet> findAll() {
        try (Session session = HibernateUtil.openSession()) {
            return session.createQuery("FROM Planet", Planet.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Planet create(String id, String name) {
        validateId(id);
        validateName(name);

        Planet planet = new Planet(id, name);

        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.persist(planet);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }

        return planet;
    }

    public void update(Planet planet) {
        validateId(planet.getId());
        validateName(planet.getName());

        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.merge(planet);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }
    }

    public void deleteById(String id) {
        validateId(id);

        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            Planet planet = session.getReference(Planet.class, id);
            session.remove(planet);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }
    }

    public void delete(Planet planet) {
        requireNonNull(planet);

        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.remove(planet);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }
    }

    private static void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    private void validateName(String name) {
        requireNonNull(name);

        if (name.length() < 1) {
            throw new RuntimeException("Planet name length can be one or greater");
        }

        if (name.length() > 500) {
            throw new RuntimeException("Planet name length can't be greater than 500 characters");
        }
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new RuntimeException("Planet id can't be null or empty");
        }

        if (!pattern.matcher(id).matches()) {
            throw new RuntimeException("Planet id can contain uppercase letters and numbers only");
        }
    }
}
