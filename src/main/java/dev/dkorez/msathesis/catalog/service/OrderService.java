package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.entity.OrderDao;
import dev.dkorez.msathesis.catalog.entity.OrderItemDao;
import dev.dkorez.msathesis.catalog.exception.NotFoundException;
import dev.dkorez.msathesis.catalog.mapper.OrderItemMapper;
import dev.dkorez.msathesis.catalog.mapper.OrderMapper;
import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import dev.dkorez.msathesis.catalog.repository.OrderItemRepository;
import dev.dkorez.msathesis.catalog.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Inject
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderDto createOrder(CreateOrderDto order) {
        OrderDao entity = OrderMapper.fromCreateDto(order);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        orderRepository.persist(entity);

        if (order.getItems() != null && ! order.getItems().isEmpty()) {
            addOrderItems(entity, order.getItems().stream().map(OrderItemMapper::fromDto).toList());
        }

        return OrderMapper.toDto(entity);
    }

    //@Transactional
    public void addOrderItems(OrderDao order, List<OrderItemDao> items) {
        if (order != null) {
            for (OrderItemDao item : items) {
                item.order = order;
                orderItemRepository.persist(item);
            }
            orderRepository.persist(order);
        }
    }

    public OrderDto findOrder(Long orderId) {
        return orderRepository.findByIdOptional(orderId)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Order " + orderId + " not found"));
    }

    public List<OrderDto> findOrdersForUser(Long userId) {
        return orderRepository.list(" userId = ?1", userId).stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Transactional
    public OrderDto updateOrderStatus(Long id, String status) {
        OrderDao entity = orderRepository.findByIdOptional(id).orElseThrow(() ->
                new NotFoundException("order " + id + " not found"));

        entity.setStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());
        orderRepository.persist(entity);

        return OrderMapper.toDto(entity);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
