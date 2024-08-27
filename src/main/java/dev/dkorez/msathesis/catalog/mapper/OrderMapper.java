package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.entity.CheckoutDao;
import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import dev.dkorez.msathesis.catalog.model.OrderItemDto;

import java.util.Collections;
import java.util.List;

public class OrderMapper {
    public static OrderDto toDto(CheckoutDao entity) {
        if (entity == null)
            return null;

        List<OrderItemDto> items = entity.getCheckoutItems() != null ?
                entity.getCheckoutItems().stream().map(OrderItemMapper::toDto).toList() :
                Collections.emptyList();

        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setStatus(entity.getStatus());
        dto.setItems(items);

        return dto;
    }

    public static CheckoutDao fromDto(OrderDto dto) {
        if (dto == null)
            return null;

        CheckoutDao entity = new CheckoutDao();
        entity.setUserId(dto.getUserId());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    public static CheckoutDao fromCreateDto(CreateOrderDto dto) {
        if (dto == null)
            return null;

        CheckoutDao entity = new CheckoutDao();
        entity.setUserId(dto.getUserId());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(dto.getStatus());

        return entity;
    }
}
