package org.example.service;

import org.example.dao.BaseDao;
import org.example.dao.TicketDao;
import org.example.model.Client;
import org.example.model.Planet;
import org.example.model.Ticket;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class TicketService {
    private BaseDao<Ticket, Long> dao;

    public TicketService() {
        dao = new TicketDao();
    }

    public Ticket findById(Long id) {
        return dao.findById(id);
    }

    public List<Ticket> findAll() {
        return dao.findAll();
    }

    public Ticket create(Client client, Planet from, Planet to) {
        requireNonNull(client);
        requireNonNull(from);
        requireNonNull(to);

        Ticket ticket = new Ticket(client, from, to);

        return dao.save(ticket);
    }

    public void delete(Ticket ticket) {
        requireNonNull(ticket);

        dao.delete(ticket);
    }

    public void deleteById(Long id) {
        requireNonNull(id);

        dao.deleteById(id);
    }


}