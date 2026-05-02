package com.example.outbox;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Repository
public class OutboxOrderEventPublisher implements OrderEventPublisher {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public OutboxOrderEventPublisher(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(OrderEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            jdbcTemplate.update("""
                INSERT INTO outbox (id, aggregate_type, aggregate_id, type, payload)
                VALUES (?, 'ORDER', ?, 'OrderCreated', ?::jsonb)
            """, UUID.randomUUID(), event.orderId(), payload);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
