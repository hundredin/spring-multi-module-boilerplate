package com.spring.boilerplate.common.dao;

public interface GenericRepositoryInterface<T> {
    T save(T entity);
    Boolean delete(T entity);
    T edit(T entity);
    T find(Integer id);
}
