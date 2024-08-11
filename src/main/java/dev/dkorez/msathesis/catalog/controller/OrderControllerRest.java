package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import dev.dkorez.msathesis.catalog.service.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderControllerRest {
    private final OrderService orderService;

    @Inject
    public OrderControllerRest(OrderService orderService) {
        this.orderService = orderService;
    }

    @POST
    public OrderDto create(CreateOrderDto request) {
        return orderService.createOrder(request);
    }

    @GET
    @Path("/{id}")
    public OrderDto findOrder(@PathParam("id") Long id) {
        return orderService.findOrder(id);
    }

    @GET
    @Path("/user/{user_id}")
    public List<OrderDto> findOrdersForUser(@PathParam("user_id") Long id) {
        return orderService.findOrdersForUser(id);
    }

    @PATCH
    @Path("/{order_id}/status")
    public OrderDto updateStatus(@PathParam("order_id") Long orderId, String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @DELETE
    @Path("/{order_id}/cancel")
    public Response cancelOrder(@PathParam("order_id") Long orderId) {
        orderService.deleteOrder(orderId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
