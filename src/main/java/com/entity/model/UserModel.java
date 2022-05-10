package com.entity.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private java.util.List<Integer> authorities;

    private String imageProfile;
}
