package com.controller.api;

import com.entity.dto.ResponseDto;
import com.entity.model.ProductModel;
import com.entity.model.UserModel;
import com.service.ProductService;
import com.service.UserService;
import com.utils.FileUtilsService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserResources {

    private final UserService userService;

    public UserResources(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    public Object add(UserModel model, @RequestPart(name="file", required = false) MultipartFile file) throws IOException {
        model.setImageProfile(FileUtilsService.uploadFile(file, ""));
        if (model.getImageProfile() != null)
            model.setImageProfile("http://localhost:8080/images/".concat(model.getImageProfile()));
        System.out.println(model);
        return ResponseDto.of(this.userService.add(model), "Created");
    }

    @PostMapping("{id}")
    public Object update(@PathVariable("id") Long id, UserModel model, @RequestPart(name="file", required = false) MultipartFile file) throws IOException {
        model.setId(id);
        model.setImageProfile(FileUtilsService.uploadFile(file, ""));
        if (model.getImageProfile() != null)
            model.setImageProfile("http://localhost:8080/images/".concat(model.getImageProfile()));
        System.out.println(model);
        return ResponseDto.of(this.userService.update(model), "Update");
    }

    @DeleteMapping("{id}")
    public Object deleteById(@PathVariable("id") Long id) {
        return ResponseDto.of(this.userService.deleteById(id), "Deleted");
    }
}
