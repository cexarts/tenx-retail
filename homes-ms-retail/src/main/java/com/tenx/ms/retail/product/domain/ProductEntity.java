package com.tenx.ms.retail.product.domain;

import com.tenx.ms.retail.store.domain.StoreEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @ManyToOne(targetEntity = StoreEntity.class)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @Column(name="name")
    private String name;

    @Column(name="description", columnDefinition = "TEXT")
    @Lob
    private String description;

    @Column(name="sku")
    private String sku;

    @Column(name="price", precision=10, scale=2)
    private BigDecimal price;



}
