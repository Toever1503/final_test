package com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<M, D, K> {
    D findById(K id);
    Page<D> findAll(Pageable page);
    List<D> findAll();
    Page<D> search(String q ,Pageable page);
    D add(M model);
    D update(M model);
    boolean deleteById(K id);
}
