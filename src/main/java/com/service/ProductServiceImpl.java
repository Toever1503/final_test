package com.service;

import com.entity.Product;
import com.entity.dto.ProductDto;
import com.entity.model.ProductModel;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;
import com.security.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    Product toEntity(ProductModel model) {
        if (model == null) return null;
        return Product.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .price(model.getPrice())
                .image(model.getImage())
                .build();
    }

    @Override
    public ProductDto findById(Long id) {
        return ProductDto.toDto(this.productRepository.findById(id).orElse(null));
    }

    @Override
    public Page<ProductDto> findAll(Pageable page) {
        return this.productRepository.findAll(page).map(ProductDto::toDto);
    }

    @Override
    public List<ProductDto> findAll() {
        return null;
    }

    @Override
    public Page<ProductDto> search(String q, Pageable page) {
        return this.productRepository.search(q, page).map(ProductDto::toDto);
    }

    @Override
    public ProductDto add(ProductModel model) {
        Calendar calendar = Calendar.getInstance();
        Product product = toEntity(model);
        product.setCreatedAt(calendar.getTime());
        product.setUpdatedAt(calendar.getTime());
        product.setCategory(this.categoryRepository.findById(model.getCategory()).orElse(null));
//        product.setCreatedBy(SecurityUtils.getCurrentLoggedUser().getUser());
        product.setCreatedBy(this.userRepository.findById(1l).orElse(null));
        return ProductDto.toDto(this.productRepository.save(product));
    }

    @Override
    public ProductDto update(ProductModel model) {
        Calendar calendar = Calendar.getInstance();
        Product product = toEntity(model);
        product.setUpdatedAt(calendar.getTime());

        Product original = this.productRepository.findById(model.getId()).orElse(null);
        if (model.getImage() == null)
            product.setImage(original.getImage());
        product.setCreatedAt(original.getCreatedAt());
//        product.setCreatedBy(SecurityUtils.getCurrentLoggedUser().getUser());
        product.setCategory(this.categoryRepository.findById(model.getCategory()).orElse(null));
        product.setCreatedBy(this.userRepository.findById(1l).orElse(null));
        return ProductDto.toDto(this.productRepository.save(product));
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            this.productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Page<ProductDto> findAllByCategoryId(Long categoryId, Pageable page) {
        return this.productRepository.findAllByCategoryId(categoryId, page).map(ProductDto::toDto);
    }

    @Override
    public Page<ProductDto> findRelatedProduct(Long productId, Long categoryId, Pageable page) {
        return this.productRepository.findAllByCategoryIdAndIdNotLike(categoryId, productId, page).map(ProductDto::toDto);
    }

}
