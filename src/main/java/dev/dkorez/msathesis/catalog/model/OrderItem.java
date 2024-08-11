package dev.dkorez.msathesis.catalog.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    Long productId;
    int quantity;
    BigDecimal price;
}
