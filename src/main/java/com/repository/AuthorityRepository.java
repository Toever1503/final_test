package com.repository;

import com.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer>, JpaSpecificationExecutor<Authority> {
    Optional<Authority> findAuthorityByAndAuthorityName(String name);
    List<Authority> findByIdIn(List<Integer> ids);
}