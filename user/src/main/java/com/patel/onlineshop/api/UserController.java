package com.patel.onlineshop.api;


import com.patel.onlineshop.data.User;
import com.patel.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> fetchUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }


}
