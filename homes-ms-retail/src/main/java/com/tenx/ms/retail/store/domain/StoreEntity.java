package com.tenx.ms.retail.store.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="store")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name", length = 50)
    private String name;

}
