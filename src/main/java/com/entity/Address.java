package com.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "province_ids", nullable = false)
    private Province provinceId;

    @ManyToOne
    @JoinColumn(name = "district_ids", nullable = false)
    private District districtId;

    @ManyToOne
    @JoinColumn(name = "ward_ids", nullable = false)
    private Ward wardId;

    @Column(name = "detail", nullable = false, length = 500)
    private String detail;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User userId;

}