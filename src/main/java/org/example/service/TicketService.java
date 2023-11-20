package org.example.service;

import org.example.model.Client;
import org.example.model.Planet;
import org.example.model.Ticket;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class TicketService {

    public Ticket findById(Long id) {
        try (Session session = HibernateUtil.openSession()) {
            return session.get(Ticket.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Ticket> findAll() {
        try (Session session = HibernateUtil.openSession()) {
            return session.createQuery("FROM Ticket", Ticket.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Ticket create(Client client, Planet from, Planet to) {
        requireNonNull(client);
        requireNonNull(from);
        requireNonNull(to);

        Ticket ticket = new Ticket(client, from, to);


        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.persist(ticket);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }

        return ticket;
    }

    public void delete(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            session.remove(ticket);

            transaction.commit();
        } catch (Exception e) {
            transactionRollback(transaction);
            e.printStackTrace();
        }
    }

    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();

            Ticket ticket = session.getReference(Ticket.class, id);
            session.remove(ticket);

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
}