package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.entity.CheckoutItemDao;
import dev.dkorez.msathesis.catalog.model.OrderItemDto;

public class OrderItemMapper {
    public static OrderItemDto toDto(CheckoutItemDao entity) {
        if (entity == null)
            return null;

        OrderItemDto dto = new OrderItemDto();
        dto.setProductId(entity.getProductId());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public static CheckoutItemDao fromDto(OrderItemDto dto) {
        if (dto == null)
            return null;

        CheckoutItemDao entity = new CheckoutItemDao();
        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}