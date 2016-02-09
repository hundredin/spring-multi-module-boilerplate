package com.spring.boilerplate.common.dao.hibernate;

import com.spring.boilerplate.common.dao.FollowRepository;
import com.spring.boilerplate.common.dao.GenericRepository;
import com.spring.boilerplate.common.domain.Follow;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FollowRepositoryImpl extends GenericRepository<Follow> implements FollowRepository {
    @Override
    public List<Follow> findUserFollow(Integer userId) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Follow> query = builder.createQuery(Follow.class);

        Root<Follow> root = query.from(Follow.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("followerId"), userId));

        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return entityManager.createQuery(query).getResultList();
    }
}
