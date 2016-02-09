package com.spring.boilerplate.common.dao.hibernate;

import com.spring.boilerplate.common.dao.GenericRepository;
import com.spring.boilerplate.common.dao.LinkRepository;
import com.spring.boilerplate.common.domain.Link;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("linkRepository")
@Transactional
public class LinkRepositoryImpl extends GenericRepository<Link> implements LinkRepository {

    public LinkRepositoryImpl() {
        super(Link.class);
    }

    @Override
    public List<Link> findByDate(Date fromDate, Date toDate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Link> query = builder.createQuery(Link.class);

        Root<Link> root = query.from(Link.class);
        query.where(builder.between(root.get("createdAt"), fromDate, toDate));

        return entityManager.createQuery(query)
                .getResultList();
    }

    @Override
    public List<Link> findBiggerThan(Integer like, Integer view) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Link> query = builder.createQuery(Link.class);

        Root<Link> root = query.from(Link.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.gt(root.get("like"), like));
        predicates.add(builder.gt(root.get("view"), view));

        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return entityManager.createQuery(query)
                .getResultList();

    }

    @Override
    public Link findByHashId(String hashId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Link> query = builder.createQuery(Link.class);

        Root<Link> root = query.from(Link.class);

        query.where(builder.equal(root.get("hashId"), hashId));

        return entityManager.createQuery(query).getSingleResult();
    }
}
