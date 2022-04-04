package com.online.notification.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notification")
public class TestRestController {

    public String testCall(){
        return "0k";
    }
}
