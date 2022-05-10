package com.service;

import com.entity.Product;
import com.entity.dto.ProductDto;
import com.entity.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService extends BaseService<ProductModel, ProductDto, Long> {
    Page<ProductDto> findAllByCategoryId(Long categoryId, Pageable page);
    Page<ProductDto> findRelatedProduct(Long productId, Long categoryId, Pageable page);

}
