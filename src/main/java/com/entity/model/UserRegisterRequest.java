package com.entity.model;

import com.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String fullName;
    private String username;
    private String password;
    private String email;

    public static User toEntity(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setFullName(userRegisterRequest.getFullName());
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(userRegisterRequest.getPassword());
        user.setEmail(userRegisterRequest.getEmail());
        return user;
    }
}
