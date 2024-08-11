package dev.dkorez.msathesis.catalog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDER_ITEM")
@Data
public class OrderItemDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    public OrderDao order;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
