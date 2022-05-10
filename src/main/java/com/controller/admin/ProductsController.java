package com.controller.admin;

import com.entity.dto.ProductDto;
import com.service.CategoryService;
import com.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/products")
public class ProductsController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductsController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String productsDashboard(@RequestParam(name = "page", required = false, defaultValue = "0") int page, Model model) {
        model.addAttribute("page_title", "Products");
        model.addAttribute("categories", categoryService.findAll(PageRequest.of(0, 999999)));
        model.addAttribute("products", productService.findAll(PageRequest.of(page, 15)));
        return "admin/product/products";
    }
}
