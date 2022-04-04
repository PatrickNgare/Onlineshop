package com.online.notification.services;

import com.google.gson.Gson;
import com.online.notification.data.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaLister {
    private final Gson gson;

    public KafkaLister(Gson gson) {
        this.gson = gson;
    }

    @KafkaListener(topics = "serge_tech_users", groupId = "group_id")
    void listenerUsers(@Payload String data) {
        User user = gson.fromJson(data, User.class);
        log.info("Sending Email For User Created ... {}", user);
    }

    @KafkaListener(topics = "serge_tech_orders", groupId = "group_id")
    void listenerOrders(@Payload String data) {
        User user = gson.fromJson(data, User.class);
        log.info("Sending Email For Order User ... {}", user);
    }
}

/**
 * TODO
 * 1.Complete Order Service
 * 2.Learn WEBCLIENT non blocking
 * 3.Kafka Installation
 */
