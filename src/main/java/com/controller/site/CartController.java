package com.controller.site;

import com.entity.dto.CartProduct;
import com.entity.dto.ProductDto;
import com.entity.dto.ResponseDto;
import com.entity.model.AddressModel;
import com.repository.ProvinceRepository;
import com.security.SecurityUtils;
import com.service.OrderService;
import com.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/user-cart")
public class CartController {

    private final ProductService productService;
    private final OrderService orderService;
    private final ProvinceRepository provinceRepository;

    public CartController(ProductService productService,
                          OrderService orderService,
                          ProvinceRepository provinceRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping
    public String cart(@SessionAttribute("Cart") Map<Long, CartProduct> cart, Model model) {
        AtomicReference<Double> total = new AtomicReference<Double>(0.0);
        cart.forEach((key, value) -> {
            total.updateAndGet(v -> v + value.getQuantity() * value.getProduct().getPrice());
        });
        model.addAttribute("total", total.get());
        try {
            if (SecurityUtils.getCurrentLoggedUser() != null) {
                model.addAttribute("provinces", this.provinceRepository.findAll());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "site/cart";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @GetMapping("add/{id}")
    public Object add(@PathVariable Long id, @RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity, @SessionAttribute("Cart") Map<Long, CartProduct> cart) {
        ProductDto product = productService.findById(id);
        if (cart.get(id) == null) {
            cart.put(id, new CartProduct(product, product.getPrice(), quantity));
        } else {
            cart.get(id).setQuantity(cart.get(id).getQuantity() + quantity);
        }
        return ResponseDto.of(cart.size(), "Add ");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @GetMapping("delete/{id}")
    public Object delete(@PathVariable Long id, @SessionAttribute("Cart") Map<Long, CartProduct> cart) {
        cart.remove(id);
        return ResponseDto.of(cart.size(), "Remove ");
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @PostMapping("/checkout")
    public Object checkout(AddressModel model, @SessionAttribute("Cart") Map<Long, CartProduct> cart) {
        String html = this.orderService.checkout(model, cart);
        if (html == null) {
            return ResponseDto.of(null, "Checkout failed");
        }
        cart.clear();
        return ResponseDto.of(html, "Checkout ");
    }
}
