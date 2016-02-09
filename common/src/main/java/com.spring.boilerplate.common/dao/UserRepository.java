package com.spring.boilerplate.common.dao;


import com.spring.boilerplate.common.domain.User;

public interface UserRepository extends GenericRepositoryInterface<User> {
    User findUserByUsername(String username);
}
