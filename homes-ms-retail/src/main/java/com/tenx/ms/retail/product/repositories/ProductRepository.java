package com.tenx.ms.retail.product.repositories;

import com.tenx.ms.retail.product.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long>{
    List<ProductEntity> findByStoreStoreId(Long storeId);
}
