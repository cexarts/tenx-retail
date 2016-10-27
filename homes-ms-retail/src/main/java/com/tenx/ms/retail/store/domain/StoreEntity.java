package com.tenx.ms.retail.store.domain;


import com.tenx.ms.retail.product.domain.ProductEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@ToString(exclude="products")
@EqualsAndHashCode(exclude={"products"})
@Table(name="store")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "store", cascade=CascadeType.REMOVE)
    private Set<ProductEntity> products;

}
