package com.service.impl;

import com.entity.User;
import com.entity.dto.UserDto;
import com.entity.dto.UserResetPasswordRequest;
import com.entity.model.UserModel;
import com.entity.model.UserRegisterRequest;
import com.repository.AuthorityRepository;
import com.repository.UserRepository;
import com.security.SecurityUtils;
import com.service.CustomUserDetail;
import com.service.MailService;
import com.service.UserService;
import com.utils.Base64Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, MailService mailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;

//        CustomUserDetail customUserDetail = new CustomUserDetail(this.userRepository.findById(1l).orElseThrow(() -> new RuntimeException("authority admin not found")));
//        Authentication auth = new UsernamePasswordAuthenticationToken(customUserDetail, null, customUserDetail.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        this.userRepository.save(new User(null, "admin", passwordEncoder.encode("1234"), "admin", "admin@123.com", true, 0, 0, "", new Date(), new Date(), List.of(this.authorityRepository.findAuthorityByAndAuthorityName(SecurityUtils.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role not found")))));
//        this.userRepository.save(new User(null, "user", passwordEncoder.encode("1234"), "admin", "user@123.com", true, 0, 0, "", new Date(), new Date(), List.of(this.authorityRepository.findAuthorityByAndAuthorityName(SecurityUtils.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found")))));
    }

    User toEntity(UserModel model) {
        if (model == null) return null;
        return User.builder()
                .username(model.getUsername())
                .password(model.getPassword())
                .email(model.getEmail())
                .fullName(model.getFullName())
                .imageProfile(model.getImageProfile())
                .build();
    }

    @Override
    public UserDto findById(Long id) {
        return
                UserDto.toDto(this.userRepository.findById(id).orElse(null));
    }

    @Override
    public Page<UserDto> findAll(Pageable page) {
        return this.userRepository.findAll(page).map(UserDto::toDto);
    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public Page<UserDto> search(String q, Pageable page) {
        return this.userRepository.search(q, page).map(UserDto::toDto);
    }

    @Override
    public UserDto add(UserModel model) {
        Calendar calendar = Calendar.getInstance();
        User user = toEntity(model);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(calendar.getTime());
        user.setUpdatedAt(calendar.getTime());
        user.setStatus(false);
        user.setTimeFailed(0);
        user.setActiveCode(0);
        if (model.getAuthorities() == null || model.getAuthorities().isEmpty()) {
            user.setAuthorities(List.of(this.authorityRepository.findAuthorityByAndAuthorityName(SecurityUtils.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found"))));
        } else
            user.setAuthorities(this.authorityRepository.findByIdIn(model.getAuthorities()));
        return UserDto.toDto(this.userRepository.save(user));
    }

    @Override
    public UserDto update(UserModel model) {
        User original = this.userRepository.findById(model.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        original.setUsername(model.getUsername());
        original.setEmail(model.getEmail());
        original.setFullName(model.getFullName());
        original.setImageProfile(model.getImageProfile());
        if (model.getPassword() != null && !model.getPassword().isEmpty()) {
            original.setPassword(passwordEncoder.encode(model.getPassword()));
        }
        if (model.getAuthorities() != null && !model.getAuthorities().isEmpty()) {
            original.setAuthorities(this.authorityRepository.findByIdIn(model.getAuthorities()));
        }
         return UserDto.toDto(this.userRepository.save(original));
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            this.userRepository.deleteById(id);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean register(UserRegisterRequest userRegisterRequest) {
        Calendar calendar = Calendar.getInstance();
        User user = UserRegisterRequest.toEntity(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActiveCode(getCodeRandom());
        user.setCreatedAt(calendar.getTime());
        user.setUpdatedAt(calendar.getTime());
        user.setStatus(false);
        user.setTimeFailed(0);
        user.setAuthorities(Collections
                .singletonList(this.authorityRepository.findAuthorityByAndAuthorityName(SecurityUtils.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Role not found"))));

        user = this.userRepository.save(user);
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("username", user.getUsername());
            model.put("active_link", "http://localhost:8080/account/active/" + Base64Utils.encode(user.getUsername()) + "/" + Base64Utils.encode(user.getActiveCode().toString()));
            mailService.sendMail("user_activation", user.getEmail(), "Active your account", model);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return user != null;
    }

    @Override
    public boolean activeAccount(String username, int activeCode) {
        User user = this.userRepository.findByUsername(username);
        if (user == null)
            return false;
        else if (activeCode != user.getActiveCode())
            return false;
        user.setStatus(true);
        user.setActiveCode(0);
        user.setUpdatedAt(new Date());
        return this.userRepository.save(user) != null;
    }

    @Override
    public boolean forgetPassword(String username) {
        List<User> users = this.userRepository.findByUsernameOrEmail(username, username);
        if (users.size() == 0)
            return false;
        User user = users.get(0);
        user.setActiveCode(getCodeRandom());
        user.setUpdatedAt(new Date());
        Map<String, Object> model = new HashMap<>();
        model.put("username", user.getUsername());
        model.put("reset_link", "http://localhost:8080/account/reset-password/" + Base64Utils.encode(user.getId().toString()) + "/" + Base64Utils.encode(user.getActiveCode().toString()));
        try {
            this.mailService.sendMail("forget_password", user.getEmail(), "Reset your password", model);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean resetPassword(UserResetPasswordRequest userResetPasswordRequest) {
        User user = this.userRepository.findById(userResetPasswordRequest.getId()).orElse(null);
        if (user == null) return false;
        else if (userResetPasswordRequest.getCode() != user.getActiveCode()) return false;
        user.setPassword(passwordEncoder.encode(userResetPasswordRequest.getPassword()));
        user.setActiveCode(0);
        user.setUpdatedAt(new Date());
        return this.userRepository.save(user) != null;
    }

    int getCodeRandom() {
        return 100000+ (int) (Math.random() * 999999);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = this.userRepository.findByUsernameOrEmail(username, username);
        if (users.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new CustomUserDetail(users.get(0));
    }

}
