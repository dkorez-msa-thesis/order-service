package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.entity.CheckoutDao;
import dev.dkorez.msathesis.catalog.entity.CheckoutItemDao;
import dev.dkorez.msathesis.catalog.exception.NotFoundException;
import dev.dkorez.msathesis.catalog.mapper.OrderItemMapper;
import dev.dkorez.msathesis.catalog.mapper.OrderMapper;
import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import dev.dkorez.msathesis.catalog.repository.CheckoutItemRepository;
import dev.dkorez.msathesis.catalog.repository.CheckoutRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderService {
    private final CheckoutRepository checkoutRepository;
    private final CheckoutItemRepository checkoutItemRepository;

    @Inject
    public OrderService(CheckoutRepository checkoutRepository, CheckoutItemRepository checkoutItemRepository) {
        this.checkoutRepository = checkoutRepository;
        this.checkoutItemRepository = checkoutItemRepository;
    }

    @Transactional
    public OrderDto createOrder(CreateOrderDto order) {
        CheckoutDao entity = OrderMapper.fromCreateDto(order);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        checkoutRepository.persist(entity);

        if (order.getItems() != null && ! order.getItems().isEmpty()) {
            addOrderItems(entity, order.getItems().stream().map(OrderItemMapper::fromDto).toList());
        }

        return OrderMapper.toDto(entity);
    }

    //@Transactional
    public void addOrderItems(CheckoutDao order, List<CheckoutItemDao> items) {
        if (order != null) {
            for (CheckoutItemDao item : items) {
                item.checkout = order;
                checkoutItemRepository.persist(item);
            }
            checkoutRepository.persist(order);
        }
    }

    public OrderDto findOrder(Long orderId) {
        return checkoutRepository.findByIdOptional(orderId)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Order " + orderId + " not found"));
    }

    public List<OrderDto> findOrdersForUser(Long userId) {
        return checkoutRepository.list(" userId = ?1", userId).stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Transactional
    public OrderDto updateOrderStatus(Long id, String status) {
        CheckoutDao entity = checkoutRepository.findByIdOptional(id).orElseThrow(() ->
                new NotFoundException("order " + id + " not found"));

        entity.setStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());
        checkoutRepository.persist(entity);

        return OrderMapper.toDto(entity);
    }

    @Transactional
    public void deleteOrder(Long id) {
        checkoutRepository.deleteById(id);
    }
}
