package com.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "ip_from")
    private String ipFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_date", nullable = false)
    private Date commentDate;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "comment_parent")
    private Long commentParent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentUser;

}