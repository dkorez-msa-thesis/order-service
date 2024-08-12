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
public class PaymentEventConsumer {
    private final Logger logger = LoggerFactory.getLogger(PaymentEventConsumer.class);

    private final ObjectMapper objectMapper;
    private final OrderCoordinator orderCoordinator;

    @Inject
    public PaymentEventConsumer(OrderCoordinator orderCoordinator, ObjectMapper objectMapper) {
        this.orderCoordinator = orderCoordinator;
        this.objectMapper = objectMapper;
    }

    @Incoming("payment-events")
    public CompletionStage<Void> processUpdates(String event) {
        try {
            logger.info("incoming order-events: {}", event);
            PaymentEvent paymentEvent = objectMapper.readValue(event, PaymentEvent.class);

            if (paymentEvent.getType() == PaymentEventType.UPDATED) {
                // TODO: check event status and update order status
                String status = paymentEvent.getPayment().getPaymentStatus();
                orderCoordinator.updateOrderStatus(paymentEvent.getPayment().getOrderId(), status, true);
            }
        } catch (JsonProcessingException e) {
            logger.error("error processing event: {}", e.getMessage(), e);
        }

        return CompletableFuture.completedFuture(null);
    }
}
