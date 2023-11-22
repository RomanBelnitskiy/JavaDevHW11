package org.example.service;

import org.example.dao.PlanetDao;
import org.example.dao.UpdatableDao;
import org.example.model.Planet;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public class PlanetService {
    private final Pattern pattern = Pattern.compile("^[A-Z0-9]+$");

    private UpdatableDao<Planet, String> dao;

    public PlanetService() {
        dao = new PlanetDao();
    }


    public Planet findById(String id) {
        return dao.findById(id);
    }

    public List<Planet> findAll() {
        return dao.findAll();
    }

    public Planet create(String id, String name) {
        validateId(id);
        validateName(name);

        Planet planet = new Planet(id, name);

        return dao.save(planet);
    }

    public void update(Planet planet) {
        validateId(planet.getId());
        validateName(planet.getName());

        dao.update(planet);
    }

    public void deleteById(String id) {
        validateId(id);

        dao.deleteById(id);
    }

    public void delete(Planet planet) {
        requireNonNull(planet);

        dao.delete(planet);
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
