
package com.entity.dto;

import com.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;

    private String cusName;
    private String phone;

    private String status;

    private Double totalPrice;

    private Date createDate;

    private Date updateDate;

    private UserDto user;

    public static OrderDto toDto(Order order) {
        if (order == null) return null;
        return OrderDto.builder()
                .id(order.getId())
                .cusName(order.getCusName())
                .phone(order.getPhone())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .createDate(order.getCreateDate())
                .updateDate(order.getUpdateDate())
                .user(UserDto.toDto(order.getUser()))
                .build();
    }
}
