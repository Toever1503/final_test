package com.controller.api;

import com.entity.dto.ResponseDto;
import com.entity.dto.UserDto;
import com.service.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class ApiAccountController {

    private final UserService userService;

    public ApiAccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseDto login(@ModelAttribute("userDto")UserDto userDto){
        return ResponseDto.of(userService.loginByUserPassWord(userDto),"login success");
    }

}
