package com.onlineshop.order.services.impl;

import com.google.gson.Gson;
import com.onlineshop.order.data.model.Order;
import com.onlineshop.order.data.repo.OrderRepo;
import com.onlineshop.order.dto.User;
import com.onlineshop.order.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Controller
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private WebClient webClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    public OrderServiceImpl(OrderRepo orderRepo, KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.orderRepo = orderRepo;
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
        webClient = WebClient.create("http://localhost:8901/user/");
    }

    @Override
    public Order makeOrder(Order order) {
        //getCheck username exists if exists send notification using kafka
        Mono<User> userMono = webClient.get().uri(order.getUsername()).retrieve().bodyToMono(User.class);
        Optional<User> optionalUser = userMono.blockOptional();
        optionalUser.ifPresent(user -> {
            log.info("User {}", user);
            if (user.getEmail() != null) {
                //send notification
                kafkaTemplate.send("serge_tech_orders", gson.toJson(user));
            }
        });
        return orderRepo.save(order);
    }
}
