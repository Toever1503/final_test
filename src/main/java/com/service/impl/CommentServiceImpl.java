package com.service.impl;

import com.entity.Comment;
import com.entity.dto.CommentDto;
import com.entity.model.CommentModel;
import com.repository.CommentRepository;
import com.repository.OrderLineRepository;
import com.repository.ProductRepository;
import com.security.SecurityUtils;
import com.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductRepository productRepository;

    public CommentServiceImpl(CommentRepository commentRepository, OrderLineRepository orderLineRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.orderLineRepository = orderLineRepository;
        this.productRepository = productRepository;
    }

    Comment toEntity(CommentModel model) {
        if (model == null) return null;
        return Comment.builder()
                .content(model.getContent())
                .rating(model.getRating())
                .ipFrom(model.getIpFrom())
                .build();
    }

    @Override
    public CommentDto findById(Long id) {
        return null;
    }

    @Override
    public Page<CommentDto> findAll(Pageable page) {
        return null;
    }

    @Override
    public List<CommentDto> findAll() {
        return null;
    }

    @Override
    public Page<CommentDto> search(String q, Pageable page) {
        return null;
    }

    @Transactional
    @Override
    public CommentDto add(CommentModel model) {
        Comment comment = toEntity(model);
        comment.setProductId(this.productRepository.findById(model.getProductId()).orElseThrow(() -> new RuntimeException("Product not found")));
        comment.setCommentDate(Calendar.getInstance().getTime());
        comment.setCommentUser(SecurityUtils.getCurrentLoggedUser().getUser());
        comment.setCommentParent(model.getCommentParent() == null ? 0: model.getCommentParent());
        return CommentDto.toDto(this.commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentDto update(CommentModel model) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public List<CommentDto> findAllByProduct(Long product) {
        return this.commentRepository.findAllByProductIdId(product).stream().map(CommentDto::toDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean canRating(Long product) {
        try {
            Long userId = SecurityUtils.getCurrentLoggedUser().getUser().getId();
            return
//                    this.commentRepository.findAllByProductIdIdAndCommentUserId(product, userId).size() == 0 &&
                    this.orderLineRepository.hasUserBuy(product, userId).size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
