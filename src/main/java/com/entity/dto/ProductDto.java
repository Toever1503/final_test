package com.entity.dto;

import com.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;

    private Date createdAt;
    private Date updatedAt;
    private CategoryDto category;
    private UserDto createdBy;

    public static ProductDto toDto(Product productEntity) {
        if(productEntity == null) return null;
        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .image(productEntity.getImage())
                .createdAt(productEntity.getCreatedAt())
                .updatedAt(productEntity.getUpdatedAt())
                .category(CategoryDto.toDto(productEntity.getCategory()))
                .createdBy(UserDto.toDto(productEntity.getCreatedBy()))
                .build();
    }
}
