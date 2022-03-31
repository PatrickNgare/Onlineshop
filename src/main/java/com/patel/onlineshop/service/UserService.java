package com.patel.onlineshop.service;

import com.patel.onlineshop.data.User;

public interface UserService {

    User createUser(User user);

    User getByUsername(String username);
}
