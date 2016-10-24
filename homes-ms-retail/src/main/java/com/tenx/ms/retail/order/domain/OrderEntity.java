package com.tenx.ms.retail.order.domain;


import com.tenx.ms.retail.store.domain.StoreEntity;
import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name ="purchase_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @ManyToOne(targetEntity = StoreEntity.class)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @Column(name = "email")
    private String email;

    @Column(name = "order_date", nullable = false)
    private Timestamp orderDate;

    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

}
