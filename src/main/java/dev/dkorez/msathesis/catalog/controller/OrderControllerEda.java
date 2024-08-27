package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import dev.dkorez.msathesis.catalog.service.OrderCoordinator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("eda/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderControllerEda {
    private final OrderCoordinator orderCoordinator;
    private final static boolean SEND_EVENT = true;

    @Inject
    public OrderControllerEda(OrderCoordinator orderCoordinator) {
        this.orderCoordinator = orderCoordinator;
    }

    @POST
    public OrderDto create(CreateOrderDto request) {
        return orderCoordinator.createOrder(request, SEND_EVENT);
    }

    @GET
    @Path("/{id}")
    public OrderDto findOrder(@PathParam("id") Long id) {
        return orderCoordinator.findOrder(id);
    }

    @GET
    @Path("/user/{user_id}")
    public List<OrderDto> findOrdersForUser(@PathParam("user_id") Long id) {
        return orderCoordinator.findOrdersForUser(id);
    }

    @PATCH
    @Path("/{order_id}/status")
    public OrderDto updateStatus(@PathParam("order_id") Long orderId, String status) {
        return orderCoordinator.updateOrderStatus(orderId, status, SEND_EVENT);
    }

    @DELETE
    @Path("/{order_id}/cancel")
    public Response cancelOrder(@PathParam("order_id") Long orderId) {
        orderCoordinator.deleteOrder(orderId, SEND_EVENT);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
