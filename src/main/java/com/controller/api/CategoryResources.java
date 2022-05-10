package com.controller.api;

import com.entity.dto.ResponseDto;
import com.entity.model.CategoryModel;
import com.service.CategoryService;
import com.utils.ASCIIConverter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryResources {

    private final CategoryService categoryService;

    public CategoryResources(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    public Object add(CategoryModel model) {
        model.setSlug(ASCIIConverter.utf8ToAscii(model.getName().toLowerCase()));
        System.out.println(model);
        return ResponseDto.of(this.categoryService.add(model), "Create");
    }

    @PostMapping("{id}")
    public Object update(@PathVariable("id") Long id, CategoryModel model) {
        model.setId(id);
        if (model.getSlug() != null || model.getSlug().isEmpty()) {
            model.setSlug(ASCIIConverter.utf8ToAscii(model.getSlug().toLowerCase()));
        } else {
            model.setSlug(ASCIIConverter.utf8ToAscii(model.getName().toLowerCase()));
        }
        System.out.println(model);
        return ResponseDto.of(this.categoryService.update(model), "Update");
    }

    @DeleteMapping("{id}")
    public Object deleteById(@PathVariable("id") Long id) {
        return ResponseDto.of(this.categoryService.deleteById(id), "Delete");
    }
}
