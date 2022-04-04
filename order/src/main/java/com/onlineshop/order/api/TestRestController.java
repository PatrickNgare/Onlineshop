package com.onlineshop.order.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class TestRestController {

    public String testCall(){
        return "0k";
    }
}
