package com.service.ordering.order.repository;

import com.service.ordering.order.entity.Invoice;
import com.service.ordering.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Optional<Invoice> findByOrder(Order order);
}
