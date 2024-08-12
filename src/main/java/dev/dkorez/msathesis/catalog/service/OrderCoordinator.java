package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.messaging.*;
import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import jakarta.inject.Inject;

public class OrderCoordinator {
    private final OrderService orderService;
    private final OrderEventProducer eventProducer;

    @Inject
    public OrderCoordinator(OrderService orderService, OrderEventProducer eventProducer) {
        this.orderService = orderService;
        this.eventProducer = eventProducer;
    }

    public void createOrder(CreateOrderDto request, boolean sendEvent) {
        OrderDto order = orderService.createOrder(request);

        if (sendEvent) {
            OrderEvent event = new OrderEvent(OrderEventType.CREATED, order.getId(), order);
            eventProducer.sendEvent(event);
        }
    }

    public void updateOrderStatus(Long id, String status, boolean sendEvent) {
        OrderDto order = orderService.updateOrderStatus(id, status);

        if (sendEvent) {
            OrderEvent event = new OrderEvent(OrderEventType.UPDATED, id, order);
            eventProducer.sendEvent(event);
        }
    }

    public void deleteOrder(Long id, boolean sendEvent) {
        orderService.deleteOrder(id);

        if (sendEvent) {
            OrderEvent event = new OrderEvent(OrderEventType.CANCELLED, id, null);
            eventProducer.sendEvent(event);
        }
    }
}
