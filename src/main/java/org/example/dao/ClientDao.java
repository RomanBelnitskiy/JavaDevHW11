package org.example.dao;

import org.example.model.Client;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ClientDao implements UpdatableDao<Client, Long> {
    @Override
    public Client findById(Long id) {
        try (Session session = HibernateUtil.openSession()) {
            return session.get(Client.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Client> findAll() {
        try (Session session = HibernateUtil.openSession()) {
            return session.createQuery("FROM Client", Client.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public Client save(Client client) {
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

    @Override
    public void delete(Client client) {
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

    @Override
    public void deleteById(Long id) {
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

    @Override
    public void update(Client client) {
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
}
