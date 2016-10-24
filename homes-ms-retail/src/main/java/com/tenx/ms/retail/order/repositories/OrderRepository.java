package com.tenx.ms.retail.order.repositories;

import com.tenx.ms.retail.order.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long>{
}
