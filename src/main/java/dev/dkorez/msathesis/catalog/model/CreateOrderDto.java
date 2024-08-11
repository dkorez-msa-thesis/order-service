package dev.dkorez.msathesis.catalog.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderDto {
    Long userId;
    List<OrderItem> items;
    BigDecimal totalAmount;
    String status;
}
