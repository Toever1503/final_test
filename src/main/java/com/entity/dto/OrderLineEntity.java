package com.entity.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderLineEntity {
    private Long id;

    private Long order;

    private ProductDto product;

    private Integer quantity;

    private Double price;

}
