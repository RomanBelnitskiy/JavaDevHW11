package org.example.dao;

import org.example.model.Client;

import java.util.List;

import static org.example.util.HibernateUtil.*;

public class ClientDao implements UpdatableDao<Client, Long> {
    @Override
    public Client findById(Long id) {
        return inSessionReturnEntity(session -> session.get(Client.class, id));
    }

    @Override
    public List<Client> findAll() {
        return inSessionReturnList(
                session -> session.createQuery("FROM Client", Client.class).list()
        );
    }

    @Override
    public Client save(Client client) {
        inSession(session-> session.persist(client));
        return client;
    }

    @Override
    public void delete(Client client) {
        inSession(session -> session.remove(client));
    }

    @Override
    public void deleteById(Long id) {
        inSession(session -> {
            Client client = session.getReference(Client.class, id);
            session.remove(client);
        });
    }

    @Override
    public void update(Client client) {
        inSession(session -> session.merge(client));
    }
}
