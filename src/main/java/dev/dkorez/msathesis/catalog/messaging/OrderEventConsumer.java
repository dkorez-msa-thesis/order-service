package dev.dkorez.msathesis.catalog.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dkorez.msathesis.catalog.service.OrderCoordinator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderEventConsumer {
    private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final ObjectMapper objectMapper;
    private final OrderCoordinator orderCoordinator;

    @Inject
    public OrderEventConsumer(OrderCoordinator orderCoordinator, ObjectMapper objectMapper) {
        this.orderCoordinator = orderCoordinator;
        this.objectMapper = objectMapper;
    }

    @Incoming("order-events")
    public CompletionStage<Void> processUpdates(String event) {
        try {
            logger.info("incoming order-events: {}", event);
            InventoryEvent inventoryEvent = objectMapper.readValue(event, InventoryEvent.class);

            switch (inventoryEvent.getType()) {
                //case INVENTORY_RELEASED ->
                //case INVENTORY_RESERVED ->
                case PAYMENT_PROCESSED -> orderCoordinator.updateOrderStatus(inventoryEvent.getOrderId(), "COMPLETED");
            }
        } catch (JsonProcessingException e) {
            logger.error("error processing event: {}", e.getMessage(), e);
        }

        return CompletableFuture.completedFuture(null);
    }
}
