package com.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {
    private ProductDto product;
    private Double price;
    private Integer quantity;
}
