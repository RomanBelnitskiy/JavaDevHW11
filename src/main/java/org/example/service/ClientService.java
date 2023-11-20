package org.example.service;

import org.example.model.Client;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ClientService {
    public Client getById(long id) {
        try (Session session = HibernateUtil.openSession()) {
            return session.get(Client.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Client> findAll() {
        try (Session session = HibernateUtil.openSession()) {
            return session.createQuery("FROM Client", Client.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Client create(String name) {
        validateName(name);

        Client client = new Client(name);

        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.persist(client);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }

        return client;
    }

    public void update(Client client) {
        validateId(client.getId());
        validateName(client.getName());

        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.merge(client);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }
    }

    public void deleteById(Long id) {
        validateId(id);

        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            Client client = session.getReference(Client.class, id);
            session.remove(client);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }
    }

    public void delete(Client client) {
        requireNonNull(client);

        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.remove(client);

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

        if (name.length() < 3) {
            throw new RuntimeException("Client name length should be greater than 2 character");
        }

        if (name.length() > 200) {
            throw new RuntimeException("Client name length should be smaller than 200 characters");
        }
    }

    private void validateId(long id) {
        if (id < 1) {
            throw new RuntimeException("Client id can't be less than 1");
        }
    }
}
