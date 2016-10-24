package com.tenx.ms.retail.stock.services;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repositories.ProductRepository;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    private final static EntityConverter<Stock, StockEntity> CONVERTER = new EntityConverter<>(Stock.class, StockEntity.class);

    public Stock getStockByProductId(Long productId) {
        StockEntity stockEntity = stockRepository.findOne(productId);
        return CONVERTER.toT1(stockEntity);
    }

    public void editStock(Stock stock) {
        ProductEntity productEntity = productRepository.getOne(stock.getProductId());
        if (productEntity.getProductId() == 0 || !productEntity.getStore().getStoreId().equals(stock.getStoreId())) {
            throw new EntityNotFoundException("No product found with id: "+stock.getProductId()+" and store id: "+stock.getStoreId());
        }
        StockEntity entity = stockRepository.findOne(stock.getProductId());
        if (entity == null) {
            entity = CONVERTER.toT2(stock);
            entity.setProduct(productEntity);
            // Did this 'cause it was requested to have the store and the product inside the stock, but with the product is enough!
            entity.setStore(productEntity.getStore());

        }
        entity.setCount(stock.getCount());
        stockRepository.save(entity);
    }
}
