package com.entity.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderLineModel {
    private Long id;

    private Long order;

    private Long product;

    private Integer quantity;

    private Double price;

    private Double total;
}
