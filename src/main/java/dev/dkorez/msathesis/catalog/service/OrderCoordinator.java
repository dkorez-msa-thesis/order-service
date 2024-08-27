package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.messaging.*;
import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class OrderCoordinator {
    private final OrderService orderService;
    private final OrderEventProducer eventProducer;

    @Inject
    public OrderCoordinator(OrderService orderService, OrderEventProducer eventProducer) {
        this.orderService = orderService;
        this.eventProducer = eventProducer;
    }

    public OrderDto findOrder(Long orderId) {
        return orderService.findOrder(orderId);
    }

    public List<OrderDto> findOrdersForUser(Long userId) {
        return orderService.findOrdersForUser(userId);
    }

    public OrderDto createOrder(CreateOrderDto request, boolean sendEvent) {
        OrderDto response = orderService.createOrder(request);

        if (sendEvent) {
            OrderEvent event = new OrderEvent(OrderEventType.CREATED, response.getId(), response);
            eventProducer.sendEvent(event);
        }

        return response;
    }

    public OrderDto updateOrderStatus(Long id, String status, boolean sendEvent) {
        OrderDto response = orderService.updateOrderStatus(id, status);

        if (sendEvent) {
            OrderEvent event = new OrderEvent(OrderEventType.UPDATED, id, response);
            eventProducer.sendEvent(event);
        }

        return response;
    }

    public void deleteOrder(Long id, boolean sendEvent) {
        orderService.deleteOrder(id);

        if (sendEvent) {
            OrderEvent event = new OrderEvent(OrderEventType.CANCELLED, id, null);
            eventProducer.sendEvent(event);
        }
    }
}
