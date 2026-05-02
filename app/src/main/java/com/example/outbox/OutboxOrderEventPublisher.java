package com.example.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class OutboxOrderEventPublisher implements OrderEventPublisher {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public OutboxOrderEventPublisher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = new ObjectMapper();
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
