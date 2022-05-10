package com.entity.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentModel {
    private Long id;

    @NotNull
    private Long productId;

    private Float rating;

    private String ipFrom;

    private String content;

    private Long commentParent;

    private Long commentUser;
}
