package com.spring.boilerplate.common.dao;

import com.spring.boilerplate.common.domain.Follow;

import java.util.List;

public interface FollowRepository extends GenericRepositoryInterface<Follow>{

    List<Follow> findUserFollow(Integer userId);
}
