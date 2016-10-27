package com.tenx.ms.retail.stock.domain;

import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "stock")
public class StockEntity {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @OneToOne(targetEntity = StoreEntity.class)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @Column(name= "count")
    private Long count;

}
