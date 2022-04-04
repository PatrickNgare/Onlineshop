package com.patel.onlineshop.service.impl;

import com.google.gson.Gson;
import com.patel.onlineshop.data.User;
import com.patel.onlineshop.data.UserRepo;
import com.patel.onlineshop.service.UserService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserServiceImpl(UserRepo userRepo, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepo = userRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public User createUser(User user) {
        //send a notification
        Gson gson = new Gson();
        User userSave = userRepo.save(user);
        kafkaTemplate.send("serge_tech_users", gson.toJson(userSave));
        return userSave;
    }

    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElse(new User());
    }
}
