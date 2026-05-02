package com.example.outbox;

import java.math.BigDecimal;

public record OrderEvent(String orderId, BigDecimal amount) {

}
