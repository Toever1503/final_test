package com.controller.admin;

import com.service.CategoryService;
import com.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String categoriesDashboard(@RequestParam(name = "page", required = false, defaultValue = "0") int page, Model model) {
        model.addAttribute("page_title", "Categories");
        model.addAttribute("categories", categoryService.findAll(PageRequest.of(page, 15)));
        return "admin/category/categories";
    }
}
