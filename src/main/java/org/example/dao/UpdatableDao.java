package org.example.dao;

public interface UpdatableDao<T, K> extends BaseDao<T, K> {
    void update(T entity);
}
