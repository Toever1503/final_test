package com.service;

import com.entity.dto.CartProduct;
import com.entity.dto.OrderDto;
import com.entity.model.AddressModel;
import com.entity.model.OrderModel;

import java.util.Map;

public interface OrderService extends BaseService<OrderModel, OrderDto, Long> {
    String checkout(AddressModel model, Map<Long, CartProduct> cart);
}
