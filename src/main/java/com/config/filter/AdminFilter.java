package com.config.filter;

import com.entity.User;
import com.security.SecurityUtils;
import com.service.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        String redirect = "/login?force=true&redirect=" + req.getServletPath();
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null) {
//            res.sendRedirect(redirect);
//            return;
//        } else {
//            if (!authentication.getAuthorities().stream().anyMatch(au -> au.getAuthority().equals("ROLE_ADMIN"))) {
//                res.sendRedirect(redirect);
//                return;
//            }
//        }
        filterChain.doFilter(req, res);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
