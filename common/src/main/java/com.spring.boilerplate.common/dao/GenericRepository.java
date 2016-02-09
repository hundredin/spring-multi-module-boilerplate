package com.spring.boilerplate.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericRepository<T> implements GenericRepositoryInterface<T> {
    protected EntityManager entityManager;
    private Class<T> type;

    public GenericRepository() {
    }

    public GenericRepository(Class<T> type) {
        this.type = type;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public Boolean delete(T entity) {
        try {
            entityManager.remove(entity);
        } catch (Exception ex) {
            return false;
        }
        return true;

    }

    @Override
    public T edit(T entity) {
        try {
            return entityManager.merge(entity);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public T find(Integer id) {
        return entityManager.find(type, id);
    }
}
