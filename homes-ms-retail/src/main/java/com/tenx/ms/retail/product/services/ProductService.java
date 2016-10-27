package com.tenx.ms.retail.product.services;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.repositories.ProductRepository;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    private final static EntityConverter<Product, ProductEntity> CONVERTER = new EntityConverter<>(Product.class, ProductEntity.class);

    public Product getProductById(Long id) {
        ProductEntity productEntity = productRepository.getOne(id);
        if (productEntity.getProductId() == 0) {
            throw new EntityNotFoundException("No product with id: "+id);
        }
        return CONVERTER.toT1(productEntity);
    }

    public List<Product> getProducts(Long storeId) {
        if(storeRepository.getOne(storeId).getStoreId() == 0 ) {
            throw new EntityNotFoundException("No store with id: "+storeId);
        }
        List<ProductEntity> products = productRepository.findByStoreStoreId(storeId);
        return  products.stream().map(CONVERTER::toT1).collect(Collectors.toList());
    }

    public long createProduct(Product product) {
        ProductEntity entity = CONVERTER.toT2(product);
        StoreEntity store = storeRepository.getOne(product.getStoreId());
        if (store.getStoreId() == 0) {
            throw new EntityNotFoundException("No Store with id: "+product.getStoreId());
        }
        entity.setStore(store);
        ProductEntity productEntity = productRepository.save(entity);
        return productEntity.getProductId();
    }

    public void deleteProduct(Long id) {
        ProductEntity entity = productRepository.getOne(id);
        if (entity.getProductId() == 0) {
            throw new EntityNotFoundException("Product not found. Id: "+ id);
        }
        productRepository.delete(id);
    }

}
