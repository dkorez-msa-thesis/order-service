package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.entity.OrderItemDao;
import dev.dkorez.msathesis.catalog.model.OrderItem;

public class OrderItemMapper {
    public static OrderItem toDto(OrderItemDao entity) {
        if (entity == null)
            return null;

        OrderItem dto = new OrderItem();
        dto.setProductId(entity.getProductId());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public static OrderItemDao fromDto(OrderItem dto) {
        if (dto == null)
            return null;

        OrderItemDao entity = new OrderItemDao();
        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}
