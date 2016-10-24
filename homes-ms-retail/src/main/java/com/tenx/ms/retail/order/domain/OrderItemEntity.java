package com.tenx.ms.retail.order.domain;

import com.tenx.ms.retail.product.domain.ProductEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "purchase_order_item")
@Data
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderItemId;

    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(targetEntity = OrderEntity.class)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "count")
    private Long count;
}
