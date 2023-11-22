package org.example.dao;

import org.hibernate.Transaction;

import java.util.List;

public interface BaseDao<T, K> {
    T findById(K id);
    List<T> findAll();
    T save(T entity);
    void delete(T entity);
    void deleteById(K id);

    default void transactionRollback(Transaction transaction) {
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }
}
