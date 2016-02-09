package com.spring.boilerplate.common.dao.hibernate;

import com.spring.boilerplate.common.dao.GenericRepository;
import com.spring.boilerplate.common.dao.UserRepository;
import com.spring.boilerplate.common.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("userRepository")
@Transactional
public class UserRepositoryImpl extends GenericRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public User findUserByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);

        query.where(builder.equal(root.get("username"), username));

        return entityManager.createQuery(query).getSingleResult();
    }
}
