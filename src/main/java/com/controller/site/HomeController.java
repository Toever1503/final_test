package com.controller.site;

import com.entity.dto.CategoryDto;
import com.entity.dto.ProductDto;
import com.service.CategoryService;
import com.service.CommentService;
import com.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    public HomeController(ProductService productService, CategoryService categoryService, CommentService commentService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String home() {
        return "site/index";
    }

    @GetMapping("about")
    public String about() {
        return "site/about";
    }

    @GetMapping("blog")
    public String blog() {
        return "site/blog";
    }

    @GetMapping("cart")
    public String cart() {
        return "site/cart";
    }

    @GetMapping("contact-us")
    public String contactus() {
        return "site/contactus";
    }

    @GetMapping("product/{name}/{id}")
    public String product(@PathVariable("id") Long id, Model model) {
        ProductDto productDto = productService.findById(id);
        if (productDto == null) return "redirect:/404";
        model.addAttribute("product", productDto);
        model.addAttribute("comments", commentService.findAllByProduct(id));
        model.addAttribute("relatedProducts", this.productService.findRelatedProduct(id, productDto.getCategory().getId(), PageRequest.of(0, 4)));
        model.addAttribute("canRating", this.commentService.canRating(id));
//        model.addAttribute("canRating", true);
        return "site/product";
    }

    @GetMapping("shop")
    public String shop(Model model) {
        model.addAttribute("products", productService.findAll(PageRequest.of(0, 4)));
        model.addAttribute("siteCategories", categoryService.findAll());
        return "site/shop";
    }

    @GetMapping("404")
    public String page_404() {
        return "site/404";
    }


    @GetMapping("single-blog")
    public String singleblog() {
        return "site/single-blog";
    }

    @GetMapping("wishlist")
    public String wishlist() {
        return "site/wishlist";
    }

    @GetMapping("search")
    public String search(@RequestParam(name = "q", required = false, defaultValue = "") String q, Model model) {
        model.addAttribute("products", q.isEmpty() ? Page.empty() : this.productService.search(q, PageRequest.of(0, 4)));
        return "site/search";
    }

}
