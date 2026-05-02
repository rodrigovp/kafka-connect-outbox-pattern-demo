package com.example.outbox;

public interface OrderEventPublisher {

    void publish(OrderEvent event);
}
