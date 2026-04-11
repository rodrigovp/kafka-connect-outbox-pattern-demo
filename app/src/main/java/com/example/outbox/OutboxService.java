package com.example.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OutboxService {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OutboxService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createOrder() {

        String orderId = UUID.randomUUID().toString();

        OrderEvent event = new OrderEvent(orderId, new BigDecimal("100.00"));

        try {
            String payload = objectMapper.writeValueAsString(event);

            jdbcTemplate.update("""
                INSERT INTO outbox (id, aggregate_type, aggregate_id, type, payload)
                VALUES (?, 'ORDER', ?, 'OrderCreated', ?::jsonb)
            """, UUID.randomUUID(), orderId, payload);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
