package com.entity.dto;

import com.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDto {
    private Long id;

    private Long commentPostId;

    private Float rating;

    private Date commentDate;

    private String content;

    private Long commentParent;

    private UserDto commentUser;


    public int getRating() {
        return Math.round(rating);
    }

    public static CommentDto toDto(Comment comment) {
        if (comment == null) return null;
        return CommentDto.builder()
                .id(comment.getId())
                .rating(comment.getRating())
                .content(comment.getContent())
                .commentDate(comment.getCommentDate())
                .commentUser(comment.getCommentUser() == null ? null : UserDto.toDto(comment.getCommentUser()))
                .build();
    }

}
