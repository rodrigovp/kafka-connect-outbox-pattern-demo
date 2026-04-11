package com.example.outbox;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OutboxService service;

    public OrderController(OutboxService service) {
        this.service = service;
    }

    @PostMapping
    public void createOrder() {
        service.createOrder();
    }
}
