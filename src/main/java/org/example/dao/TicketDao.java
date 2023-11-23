package org.example.dao;

import org.example.model.Ticket;

import java.util.List;

import static org.example.util.HibernateUtil.*;

public class TicketDao implements BaseDao<Ticket, Long> {
    @Override
    public Ticket findById(Long id) {
        return inSessionReturnEntity(session -> session.get(Ticket.class, id));
    }

    @Override
    public List<Ticket> findAll() {
        return inSessionReturnList(
                session -> session.createQuery("FROM Ticket", Ticket.class).list()
        );
    }

    @Override
    public Ticket save(Ticket ticket) {
        inSession(session -> session.persist(ticket));

        return ticket;
    }

    @Override
    public void delete(Ticket ticket) {
        inSession(session -> session.remove(ticket));
    }

    @Override
    public void deleteById(Long id) {
        inSession(session -> {
            Ticket ticket = session.getReference(Ticket.class, id);
            session.remove(ticket);
        });
    }
}
