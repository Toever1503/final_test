package com.service.impl;

import com.entity.Category;
import com.entity.dto.CategoryDto;
import com.entity.model.CategoryModel;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    Category toEntity(CategoryModel model) {
        if (model == null) return null;
        return Category.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .build();
    }

    @Override
    public CategoryDto findById(Long id) {
        return CategoryDto.toDto(this.categoryRepository.findById(id).orElse(null));
    }

    @Override
    public Page<CategoryDto> findAll(Pageable page) {
        return this.categoryRepository.findAll(page).map(CategoryDto::toDto);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(CategoryDto::toDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Page<CategoryDto> search(String q, Pageable page) {
        return this.categoryRepository.findAllByNameLike("%".concat(q.concat("%")), page).map(CategoryDto::toDto);
    }

    @Override
    public CategoryDto add(CategoryModel model) {
        return CategoryDto.toDto(this.categoryRepository.save(toEntity(model)));
    }

    @Override
    public CategoryDto update(CategoryModel model) {
        return CategoryDto.toDto(this.categoryRepository.save(toEntity(model)));
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            this.categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
