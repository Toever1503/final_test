package com.controller.api;

import com.entity.dto.ResponseDto;
import com.entity.model.ProductModel;
import com.service.ProductService;
import com.utils.FileUtilsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/product")
public class ProductResources {

    private final ProductService productService;

    public ProductResources(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Object getProducts(Pageable pageable) {
        return ResponseDto.of(this.productService.findAll(pageable), "Get all");
    }

    @GetMapping("{id}")
    public Object getProduct(@PathVariable("id") Long id) {
        return ResponseDto.of(this.productService.findById(id), "Get");
    }

    @PostMapping
    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    public Object addProduct(ProductModel model, @RequestPart("file") MultipartFile file) throws IOException {
        model.setImage(FileUtilsService.uploadFile(file, ""));
        if (model.getImage() != null)
            model.setImage("http://localhost:8080/images/".concat(model.getImage()));
        System.out.println(model);
        return ResponseDto.of(this.productService.add(model), "Add");
    }

    @GetMapping("/category/{id}")
    public Object getProductByCategory(@PathVariable("id") Long categoryId,
                                       @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return ResponseDto.of(this.productService.findAllByCategoryId(categoryId, PageRequest.of(page, 4)), "Get category");
    }

    @PostMapping("{id}")
    public Object updateProduct(@PathVariable("id") Long id, ProductModel model, @RequestPart("file") MultipartFile file) throws IOException {
        model.setId(id);
        model.setImage(FileUtilsService.uploadFile(file, ""));
        if (model.getImage() != null)
            model.setImage("http://localhost:8080/images/".concat(model.getImage()));
        System.out.println(model);
        return ResponseDto.of(this.productService.update(model), "Update");
    }

    @DeleteMapping("{id}")
    public Object deleteProduct(@PathVariable("id") Long id) {
        return ResponseDto.of(this.productService.deleteById(id), "Deleted");
    }

    @GetMapping("/search")
    public Object searchProduct(@RequestParam(name = "q", required = false, defaultValue = "") String q,
                                @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return ResponseDto.of(this.productService.search(q, PageRequest.of(page, 4)), "Search");
    }
}
