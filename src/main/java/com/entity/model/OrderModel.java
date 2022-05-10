
package com.entity.model;

import com.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderModel {
    private Long id;

    private String phone;

    private String status;

    private Double totalPrice;

    private User user;
}
