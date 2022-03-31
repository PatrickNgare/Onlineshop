package com.patel.onlineshop.service.impl;

import com.patel.onlineshop.data.User;
import com.patel.onlineshop.data.UserRepo;
import com.patel.onlineshop.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user) {

        //
        //
        return userRepo.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElse(new User());
    }
}
