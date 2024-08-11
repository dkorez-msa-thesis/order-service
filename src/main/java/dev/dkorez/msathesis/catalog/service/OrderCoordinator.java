package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.messaging.InventoryEvent;
import dev.dkorez.msathesis.catalog.messaging.InventoryEventType;
import dev.dkorez.msathesis.catalog.messaging.OrderEventProducer;
import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import dev.dkorez.msathesis.catalog.model.OrderItem;
import jakarta.inject.Inject;

import java.util.List;

public class OrderCoordinator {
    private final OrderService orderService;
    private final OrderEventProducer eventProducer;

    @Inject
    public OrderCoordinator(OrderService orderService, OrderEventProducer eventProducer) {
        this.orderService = orderService;
        this.eventProducer = eventProducer;
    }

    public OrderDto createOrder(CreateOrderDto request) {
        OrderDto response = orderService.createOrder(request);

        List<OrderItem> items = response.getItems();
        for (OrderItem item: items) {
            InventoryEvent event = new InventoryEvent();
            event.setType(InventoryEventType.ORDER_CREATED);
            event.setProductId(item.getProductId());
            event.setQuantity(item.getQuantity());
            event.setOrderId(response.getId());

            eventProducer.sendEvent(event);
        }

        return response;
    }

    public OrderDto updateOrderStatus(Long id, String status) {
        return orderService.updateOrderStatus(id, status);
    }

    public void deleteOrder(Long id) {
        orderService.deleteOrder(id);

        InventoryEvent event = new InventoryEvent();
        event.setType(InventoryEventType.ORDER_CANCELLED);
        event.setProductId(id);
        event.setQuantity(null);
        event.setOrderId(null);

        eventProducer.sendEvent(event);
    }
}
