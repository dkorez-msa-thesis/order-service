package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.entity.OrderDao;
import dev.dkorez.msathesis.catalog.model.CreateOrderDto;
import dev.dkorez.msathesis.catalog.model.OrderDto;
import dev.dkorez.msathesis.catalog.model.OrderItemDto;

import java.util.Collections;
import java.util.List;

public class OrderMapper {
    public static OrderDto toDto(OrderDao entity) {
        if (entity == null)
            return null;

        List<OrderItemDto> items = entity.getOrderItems() != null ?
                entity.getOrderItems().stream().map(OrderItemMapper::toDto).toList() :
                Collections.emptyList();

        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setStatus(entity.getStatus());
        dto.setItems(items);

        return dto;
    }

    public static OrderDao fromDto(OrderDto dto) {
        if (dto == null)
            return null;

        OrderDao entity = new OrderDao();
        entity.setUserId(dto.getUserId());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    public static OrderDao fromCreateDto(CreateOrderDto dto) {
        if (dto == null)
            return null;

        OrderDao entity = new OrderDao();
        entity.setUserId(dto.getUserId());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(dto.getStatus());

        return entity;
    }
}
