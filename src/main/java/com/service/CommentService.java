package com.service;

import com.entity.dto.CommentDto;
import com.entity.model.CommentModel;

import java.util.List;

public interface CommentService extends BaseService<CommentModel, CommentDto, Long> {
    List<CommentDto> findAllByProduct(Long product);

    boolean canRating(Long product);
}
