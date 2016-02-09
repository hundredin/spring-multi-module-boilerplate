package com.spring.boilerplate.common.dao;

import com.spring.boilerplate.common.domain.Link;

import java.util.Date;
import java.util.List;

public interface LinkRepository extends GenericRepositoryInterface<Link> {
    List<Link> findByDate(Date fromDate, Date toDate);

    List<Link> findBiggerThan(Integer like, Integer view);

    Link findByHashId(String hashId);
}
