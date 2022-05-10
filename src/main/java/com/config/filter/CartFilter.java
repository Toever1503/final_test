package com.config.filter;

import com.entity.dto.CartProduct;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("CartFilter");
        if (request.getSession().getAttribute("Cart") == null) {
            Map<Long, CartProduct> cart = new HashMap<>();
            request.getSession().setAttribute("Cart", cart);
        }
        filterChain.doFilter(request, response);
    }
}
