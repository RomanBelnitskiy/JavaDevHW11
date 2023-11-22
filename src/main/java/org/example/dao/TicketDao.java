package org.example.dao;

import org.example.model.Ticket;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TicketDao implements BaseDao<Ticket, Long> {
    @Override
    public Ticket findById(Long id) {
        try (Session session = HibernateUtil.openSession()) {
            return session.get(Ticket.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Ticket> findAll() {
        try (Session session = HibernateUtil.openSession()) {
            return session.createQuery("FROM Ticket", Ticket.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public Ticket save(Ticket ticket) {
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

    @Override
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

    @Override
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
}
