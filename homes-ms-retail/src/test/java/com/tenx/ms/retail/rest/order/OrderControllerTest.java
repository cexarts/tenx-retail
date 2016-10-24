package com.tenx.ms.retail.rest.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.order.rest.dto.OrderResponse;
import com.tenx.ms.retail.rest.BaseResponse;
import com.tenx.ms.retail.rest.BaseTest;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RetailServiceApp.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class OrderControllerTest extends BaseTest {

    private Long storeId;
    private Long storeId2;
    private Long productId;
    private Long productId2;

    @Before
    public void createStoreProductStock() {
        ResourceCreated<Long> storeCreated = createStore(validStoreRequestJson, HttpStatus.CREATED);
        storeId = storeCreated.getId();
        ResourceCreated<Long> storeCreated2 = createStore(validStoreRequestJson, HttpStatus.CREATED);
        storeId2 = storeCreated2.getId();
        ResourceCreated<Long> productCreated = createProduct(storeId, validProductJson, HttpStatus.CREATED);
        productId = productCreated.getId();
        ResourceCreated<Long> productCreated2 = createProduct(storeId2, validProductJson, HttpStatus.CREATED);
        productId2 = productCreated2.getId();
        updateStock3Items(storeId,productId);
        updateStock3Items(storeId2,productId2);
    }

    @Test
    @FlywayTest
    public void testCreateValidOrder() {
        OrderResponse orderResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, validOrderJson, HttpMethod.POST, HttpStatus.CREATED, new TypeReference<OrderResponse>() {
        });
        assertNotNull("Response shouldn't be null", orderResponse);
        assertTrue("Products should be Empty", orderResponse.getOrderId() > 0);
    }

    @Test
    @FlywayTest
    public void testCreateValidOrderWrongProduct() {
        BaseResponse orderResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, validOrderJsonWrongProduct, HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {
        });
        assertNotNull("Response shouldn't be null", orderResponse);
    }

    @Test
    @FlywayTest
    public void testCreateOrderInvalidFields() {
        BaseResponse badEmailResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, invalidEmailOrderJson, HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {});
        assertNotNull("Response shouldn't be null", badEmailResponse);
        BaseResponse badPhoneResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, invalidPhoneOrderJson, HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {});
        assertNotNull("Response shouldn't be null", badPhoneResponse);
        BaseResponse badNameResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, invalidNameOrderJson, HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {});
        assertNotNull("Response shouldn't be null", badNameResponse);
        BaseResponse badItemsResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, invalidItemsOrderJson, HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {});
        assertNotNull("Response shouldn't be null", badItemsResponse);
        BaseResponse badCountItemsResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, invalidCountItemsOrderJson, HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {});
        assertNotNull("Response shouldn't be null", badCountItemsResponse);
        BaseResponse badProductIdResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, invalidProductIdJson, HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {});
        assertNotNull("Response shouldn't be null", badProductIdResponse);
    }

    @Test
    @FlywayTest
    public void testOrderStockModification() {
        OrderResponse orderResponse = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, validOrderJson, HttpMethod.POST, HttpStatus.CREATED, new TypeReference<OrderResponse>() {});
        assertNotNull("Response shouldn't be null", orderResponse);
        assertTrue("Products should be Empty", orderResponse.getOrderId() > 0);
        BaseResponse orderResponse2 = sendRequest(getBasePath() + ORDER_REQUEST_URI + "/" + storeId, validOrderJson, HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {});
        assertNotNull("Response shouldn't be null", orderResponse2);
    }

}
