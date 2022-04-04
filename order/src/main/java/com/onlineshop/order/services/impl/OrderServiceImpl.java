package com.onlineshop.order.services.impl;

import com.onlineshop.order.data.model.Order;
import com.onlineshop.order.data.repo.OrderRepo;
import com.onlineshop.order.services.OrderService;
import org.springframework.stereotype.Controller;

@Controller
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Order makeOrder(Order order) {
        //getCheck username exists if exists send notification using kafka

        //
        return null;
    }
}
