package com.controller;

import com.entity.User;
import com.entity.dto.UserDto;
import com.entity.dto.UserResetPasswordRequest;
import com.entity.model.UserLoginRequest;
import com.entity.model.UserRegisterRequest;
import com.service.UserService;
import com.utils.Base64Utils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("userRegisterRequest", new UserRegisterRequest());
        return "registerPage";
    }


    @PostMapping("register")
    public String registerProcessing(UserRegisterRequest userRegisterRequest, Model model) {
        System.out.println(userRegisterRequest);
        if (this.userService.register(userRegisterRequest) == true)
            model.addAttribute("message", "Account Registered successfully! Please check email to active your account");
        else
            model.addAttribute("message", "Account Registration failed");
        return "registerPage";
    }

    @GetMapping("account/active/{username}/{code}")
    public String activeAccount(@PathVariable String username, @PathVariable String code, Model model) {
        if (this.userService.activeAccount(Base64Utils.decode(username), Integer.valueOf(Base64Utils.decode(code))) == true)
            model.addAttribute("message", "Active account successfully!");
        else
            model.addAttribute("message", "Active account failed");
        return "active-account-page";
    }

    @ResponseBody
    @GetMapping("account/forget-password/{username}")
    public String loginProcessing(UserLoginRequest userLoginRequest) {
        return null;
    }

    @GetMapping("account/reset-password/{id}/{code}")
    public String resetPassword(@PathVariable String id, @PathVariable String code, Model model) {
        UserDto user = this.userService.findById(Long.valueOf(Base64Utils.decode(id)));
        if (user != null) {
            model.addAttribute("userId", user.getId());
            model.addAttribute("code", code);
            return "reset-password-page";
        }
        return "redirect:/404";
    }

    @PostMapping("account/reset-password")
    public String resetPasswordProcessing(UserResetPasswordRequest user, Model model) {
        if (this.userService.resetPassword(user) == true) {
            model.addAttribute("message", "Reset password successfully! You can login again now.");
        } else
            model.addAttribute("message", "Reset password failed");
        return "reset-password-page";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest req) throws ServletException {
        req.logout();
        req.getSession().invalidate();
        return "redirect:/login";
    }
}
