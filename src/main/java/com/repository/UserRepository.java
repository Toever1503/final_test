package com.repository;

import com.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username like %?1% or u.email like %?1% or u.fullName like %?1%")
    Page<User> search(String keyword, Pageable pageable);

    List<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);

    User findByUsername(String username);
}
