package io.github.gchape.ebookshop.repositories;

import io.github.gchape.ebookshop.entities.Order;
import io.github.gchape.ebookshop.entities.id.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, OrderId> {
}
