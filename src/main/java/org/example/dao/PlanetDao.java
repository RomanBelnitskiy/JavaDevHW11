package org.example.dao;

import org.example.model.Planet;

import java.util.List;

import static org.example.util.HibernateUtil.*;

public class PlanetDao implements UpdatableDao<Planet, String> {
    @Override
    public Planet findById(String id) {
        return inSessionReturnEntity(session -> session.get(Planet.class, id));
    }

    @Override
    public List<Planet> findAll() {
        return inSessionReturnList(
                session -> session.createQuery("FROM Planet", Planet.class).list()
        );
    }

    @Override
    public Planet save(Planet planet) {
        inSession(session -> session.persist(planet));
        return planet;
    }

    @Override
    public void delete(Planet planet) {
        inSession(session -> session.remove(planet));
    }

    @Override
    public void deleteById(String id) {
        inSession(session -> {
            Planet planet = session.getReference(Planet.class, id);
            session.remove(planet);
        });
    }

    @Override
    public void update(Planet planet) {
        inSession(session -> session.merge(planet));
    }
}
