package com.tenx.ms.retail.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.BaseIntegrationTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.File;

public class BaseTest extends BaseIntegrationTest {

    protected static final String STORES_REQUEST_URI = "/v1/stores";

    protected static final String PRODUCTS_REQUEST_URI = "/v1/products";

    protected static final String STOCK_REQUEST_URI = "/v1/stock";

    protected static final String ORDER_REQUEST_URI = "/v1/orders";

    @Value("classpath:json/stores/validStore.json")
    protected File validStoreRequestJson;

    @Value("classpath:json/stores/invalidStore.json")
    protected File notValidStoreRequestJson;

    @Value("classpath:json/products/validProduct.json")
    protected File validProductJson;

    @Value("classpath:json/products/validProduct2.json")
    protected File validProductJson2;

    @Value("classpath:json/products/invalidProductSKU.json")
    protected File notvalidProductSKUJson;

    @Value("classpath:json/products/invalidProductAmount.json")
    protected File notvalidProductAmountJson;

    @Value("classpath:json/stock/validStock.json")
    protected File validStockRequestJson3Items;

    @Value("classpath:json/stock/invalidStock.json")
    protected File invalidStockRequestJson;

    @Value("classpath:json/order/validOrder.json")
    protected File validOrderJson;

    @Value("classpath:json/order/validOrderWrongProduct.json")
    protected File validOrderJsonWrongProduct;

    @Value("classpath:json/order/invalidEmail.json")
    protected File invalidEmailOrderJson;

    @Value("classpath:json/order/invalidPhone.json")
    protected File invalidPhoneOrderJson;

    @Value("classpath:json/order/invalidName.json")
    protected File invalidNameOrderJson;

    @Value("classpath:json/order/invalidItems.json")
    protected File invalidItemsOrderJson;

    @Value("classpath:json/order/invalidCountItems.json")
    protected File invalidCountItemsOrderJson;

    @Value("classpath:json/order/invalidProductId.json")
    protected File invalidProductIdJson;


    protected ResourceCreated<Long> createStore(File jsonRequest, HttpStatus expectedStatus) {
        return sendRequest(getBasePath()+STORES_REQUEST_URI, jsonRequest, HttpMethod.POST,  expectedStatus,  new TypeReference<ResourceCreated<Long>>(){});
    }

    protected ResourceCreated<Long> createProduct(Long storeId, File jsonRequest, HttpStatus expectedStatus) {
        return sendRequest(getBasePath()+PRODUCTS_REQUEST_URI+"/"+storeId,validProductJson, HttpMethod.POST,  expectedStatus,  new TypeReference<ResourceCreated<Long>>(){});
    }

    protected ResourceCreated<Long> updateStock3Items(Long storeId, Long productId) {
        return sendRequest(getBasePath() + STOCK_REQUEST_URI + "/" + storeId + "/" + productId, validStockRequestJson3Items, HttpMethod.POST, HttpStatus.CREATED, new TypeReference<ResourceCreated<Long>>() {});
    }
}
