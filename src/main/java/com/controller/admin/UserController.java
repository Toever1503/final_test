package com.controller.admin;

import com.service.AuthorityService;
import com.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final AuthorityService authorityService;
    private final UserService userService;

    public UserController(AuthorityService authorityService, UserService userService) {
        this.authorityService = authorityService;
        this.userService = userService;
    }


    @GetMapping
    public String categoriesDashboard(@RequestParam(name = "page", required = false, defaultValue = "0") int page, Model model) {
        model.addAttribute("page_title", "Users");
        model.addAttribute("users", this.userService.findAll(PageRequest.of(page, 15)));
        model.addAttribute("authorities", this.authorityService.findAll());
        return "admin/user/users";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("disable/{id}")
    public Object disableUser(@PathVariable Long id) {
        return null;
    }
}
