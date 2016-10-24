package com.tenx.ms.retail.order.services;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.domain.OrderItemEntity;
import com.tenx.ms.retail.order.repositories.OrderItemRepository;
import com.tenx.ms.retail.order.repositories.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.rest.dto.OrderItem;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.services.StockService;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StockService stockService;

    private final static EntityConverter<Order, OrderEntity> CONVERTER = new EntityConverter<>(Order.class, OrderEntity.class);

    @Transactional
    public Order createOrder(Order order) {
        OrderEntity orderEntity = CONVERTER.toT2(order);
        StoreEntity storeEntity = storeRepository.findOne(order.getStoreId());
        if (storeEntity.getStoreId() == 0) {
            throw new EntityNotFoundException("Entity not found. Store : " + order.getStoreId());
        }
        orderEntity.setStore(storeEntity);
        orderEntity.setOrderDate(new Timestamp(new Date().getTime()));
        orderRepository.save(orderEntity);
        includeProducts(orderEntity, order);
        return CONVERTER.toT1(orderEntity);
    }

    private void includeProducts(OrderEntity orderEntity, Order order) {
        for (OrderItem item : order.getProducts()) {
            updateProductStock(item,order.getStoreId());
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setCount(item.getCount());
            orderItemEntity.setOrder(orderEntity);
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductId(item.getProductId());
            orderItemEntity.setProduct(productEntity);
            orderItemRepository.save(orderItemEntity);
        }
    }

    private void updateProductStock(OrderItem item, Long storeId) {
        Stock stock = stockService.getStockByProductId(item.getProductId());
        if (stock == null || stock.getCount() < item.getCount()) {
            throw new IllegalArgumentException("Not enough stock for product :"+item.getProductId());
        }
        stock.setCount(stock.getCount() - item.getCount());
        stock.setStoreId(storeId);
        stockService.editStock(stock);
    }


}
