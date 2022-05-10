package com.repository;

import com.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProductIdId(Long productId);

    List<Comment> findAllByProductIdIdAndCommentUserId(Long productId, Long userId);
}
