package dev.dkorez.msathesis.catalog.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class OrderEventProducer {
    @Inject
    @Channel("order-events")
    Emitter<OrderEvent> eventEmitter;

    public void sendEvent(OrderEvent event) {
        eventEmitter.send(event);
    }
}
