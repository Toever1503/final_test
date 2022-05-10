package com.service;

import com.entity.dto.UserDto;
import com.entity.dto.UserResetPasswordRequest;
import com.entity.model.UserModel;
import com.entity.model.UserRegisterRequest;

public interface UserService extends BaseService<UserModel, UserDto, Long> {
    boolean register(UserRegisterRequest userRegisterRequest);

    boolean activeAccount(String username, int activeCode);

    boolean forgetPassword(String username);

    boolean resetPassword(UserResetPasswordRequest userResetPasswordRequest);
}
