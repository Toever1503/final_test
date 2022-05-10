package com.repository;

import com.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByNameLike(String keyword, Pageable pageable);

    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    Page<Product> search(String keyword, Pageable pageable);

    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findAllByCategoryIdAndIdNotLike(Long categoryId, Long id, Pageable pageable);
}
