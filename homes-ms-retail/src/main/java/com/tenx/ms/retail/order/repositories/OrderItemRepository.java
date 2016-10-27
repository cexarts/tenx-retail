package com.tenx.ms.retail.order.repositories;

import com.tenx.ms.retail.order.domain.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long>{
}
