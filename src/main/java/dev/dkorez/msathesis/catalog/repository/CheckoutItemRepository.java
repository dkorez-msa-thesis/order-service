package dev.dkorez.msathesis.catalog.repository;

import dev.dkorez.msathesis.catalog.entity.CheckoutItemDao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CheckoutItemRepository implements PanacheRepository<CheckoutItemDao> {
}
