package com.repository;

import com.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    @Query("SELECT ol FROM OrderLine ol join Order o on o.id = ol.order.id where o.user.id = ?2 and ol.product.id = ?1")
    List<OrderLine> hasUserBuy(Long productId, Long userId);
}
