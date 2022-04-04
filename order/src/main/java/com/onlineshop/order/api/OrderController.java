package com.onlineshop.order.api;

import com.onlineshop.order.data.model.Order;
import com.onlineshop.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> makeOder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.makeOrder(order));
    }
}
