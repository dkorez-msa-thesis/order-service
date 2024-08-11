package dev.dkorez.msathesis.catalog.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    Long id;
    Long userId;
    List<OrderItem> items;
    BigDecimal totalAmount;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
