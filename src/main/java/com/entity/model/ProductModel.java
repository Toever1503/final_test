package com.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductModel {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String image;

    private Long category;

}
