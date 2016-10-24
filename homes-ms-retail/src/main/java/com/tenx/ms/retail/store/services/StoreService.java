package com.tenx.ms.retail.store.services;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repositories.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    private final static EntityConverter<Store, StoreEntity> CONVERTER = new EntityConverter<>(Store.class, StoreEntity.class);

    public Store getStoreById(Long id) {
        StoreEntity storeEntity = storeRepository.getOne(id);
        return CONVERTER.toT1(storeEntity);
    }

    public List<Store> getStores() {
        return  storeRepository.findAll().stream().map(CONVERTER::toT1).collect(Collectors.toList());
    }

    public long createStore(Store store) {
        StoreEntity entity = storeRepository.save(CONVERTER.toT2(store));
        return entity.getStoreId();
    }

    public void updateStore(Store store) {
        StoreEntity entity = storeRepository.getOne(store.getStoreId());
        if (entity != null) {
            storeRepository.save(CONVERTER.toT2(store));
        }
    }

    public void deleteStore(Long id) {
        StoreEntity entity = storeRepository.getOne(id);
        if (entity != null) {
            storeRepository.delete(id);
        }
    }

}
