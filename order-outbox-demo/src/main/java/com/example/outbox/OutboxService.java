package com.example.outbox;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

@Service
public class OutboxService {

    private OrderEventPublisher publisher;

    public OutboxService(OrderEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void createOrder() {
        String orderId = randomUUID().toString();
        OrderEvent event = new OrderEvent(orderId, new BigDecimal("100.00"));

        publisher.publish(event);
    }
}
