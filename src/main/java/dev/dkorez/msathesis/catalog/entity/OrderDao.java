package dev.dkorez.msathesis.catalog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDER")
@Data
public class OrderDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "status")
    String status;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public List<OrderItemDao> orderItems;
}
