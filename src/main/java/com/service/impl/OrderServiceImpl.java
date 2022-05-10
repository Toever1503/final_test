package com.service.impl;

import com.entity.Address;
import com.entity.Order;
import com.entity.OrderLine;
import com.entity.User;
import com.entity.dto.CartProduct;
import com.entity.dto.OrderDto;
import com.entity.model.AddressModel;
import com.entity.model.OrderModel;
import com.entity.model.OrderStatus;
import com.repository.*;
import com.security.SecurityUtils;
import com.service.MailService;
import com.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final MailService mailService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderLineRepository orderLineRepository, ProductRepository productRepository, AddressRepository addressRepository, WardRepository wardRepository, DistrictRepository districtRepository, ProvinceRepository provinceRepository, MailService mailService) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
        this.mailService = mailService;
    }

    Address modelToEntity(AddressModel addressModel) {
        if (addressModel == null) return null;
        return Address.builder()
                .id(addressModel.getId())
                .detail(addressModel.getDetail())
                .build();

    }

    @Override
    public OrderDto findById(Long id) {
        return null;
    }

    @Override
    public Page<OrderDto> findAll(Pageable page) {
        return null;
    }

    @Override
    public List<OrderDto> findAll() {
        return null;
    }

    @Override
    public Page<OrderDto> search(String q, Pageable page) {
        return null;
    }

    @Override
    public OrderDto add(OrderModel model) {
        return null;
    }

    @Override
    public OrderDto update(OrderModel model) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    @Transactional
    public String checkout(AddressModel model, Map<Long, CartProduct> cart) {
        if (cart.isEmpty()) return null;
        User user = SecurityUtils.getCurrentLoggedUser().getUser();
        Map<String, Object> mailData = new HashMap<>();
        mailData.put("cart", cart);


        Address address = modelToEntity(model);
        address.setProvinceId(this.provinceRepository.findById(model.getProvinceId()).orElseThrow(() -> new RuntimeException("Province not found")));
        address.setDistrictId(this.districtRepository.findById(model.getDistrictId()).orElseThrow(() -> new RuntimeException("District not found")));
        address.setWardId(this.wardRepository.findById(model.getWardId()).orElseThrow(() -> new RuntimeException("Ward not found")));
        address = this.addressRepository.save(address);
        Calendar calendar = Calendar.getInstance();
        Order order = Order.builder()
                .status(OrderStatus.PENDING.name())
                .cusName(model.getCusName())
                .phone(model.getPhone())
                .user(null)
                .createDate(calendar.getTime())
                .updateDate(calendar.getTime())
                .build();
        order.setPhone(model.getPhone());
        order.setCusName(model.getCusName());
        order.setAddress(address);
        order.setUser(user);
        order = this.orderRepository.save(order);

        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
        Order finalOrder = order;
        cart.forEach((key, value) -> {
            OrderLine line = OrderLine.builder()
                    .order(finalOrder)
                    .price(value.getPrice())
                    .quantity(value.getQuantity())
                    .product(this.productRepository.findById(key).orElseThrow(() -> new RuntimeException("Product not found"))).build();
            orderLines.add(line);
            totalPrice.updateAndGet(v -> v + value.getPrice() * value.getQuantity());
        });
        this.orderLineRepository.saveAll(orderLines);
        order.setTotalPrice(totalPrice.get());
        this.orderRepository.save(order);
        mailData.put("order", order);
        try {
            this.mailService.sendMail("checkout.html", user.getEmail(), "Thanks for shopping with us", mailData);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            return this.mailService.getMailContent("checkout.html", mailData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
