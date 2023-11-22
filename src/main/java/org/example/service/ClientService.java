package org.example.service;

import org.example.dao.ClientDao;
import org.example.dao.UpdatableDao;
import org.example.model.Client;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ClientService {
    private UpdatableDao<Client, Long> dao;

    public ClientService() {
        dao = new ClientDao();
    }

    public Client findById(Long id) {
        requireNonNull(id);

        return dao.findById(id);
    }

    public List<Client> findAll() {
        return dao.findAll();
    }

    public Client create(String name) {
        validateName(name);

        Client client = new Client(name);

        return dao.save(client);
    }

    public void update(Client client) {
        requireNonNull(client.getId());
        validateName(client.getName());

        dao.update(client);
    }

    public void deleteById(Long id) {
        requireNonNull(id);

        dao.deleteById(id);
    }

    public void delete(Client client) {
        requireNonNull(client);

        dao.delete(client);
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
}
