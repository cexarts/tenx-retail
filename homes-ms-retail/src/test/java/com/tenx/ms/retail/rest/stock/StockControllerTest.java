package com.tenx.ms.retail.rest.stock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailServiceApp;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RetailServiceApp.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class StockControllerTest extends BaseTest {

    private Long storeId;
    private Long productId;

    @Before
    public void createStoreProduct() {
        ResourceCreated<Long> storeCreated = createStore(validStoreRequestJson, HttpStatus.CREATED);
        storeId = storeCreated.getId();
        ResourceCreated<Long> productCreated = createProduct(storeId, validProductJson, HttpStatus.CREATED);
        productId = productCreated.getId();
    }

    @Test
    @FlywayTest
    public void testSetStock() {
        ResourceCreated<Long> stockReponse = sendRequest(getBasePath() + STOCK_REQUEST_URI + "/" + storeId + "/" + productId, validStockRequestJson3Items, HttpMethod.POST, HttpStatus.CREATED, new TypeReference<ResourceCreated<Long>>() {});
        assertNotNull("Response shouldn't be null", stockReponse);
    }

    @Test
    @FlywayTest
    public void testSetStockBadStore() {
        BaseResponse stockReponse = sendRequest(getBasePath() + STOCK_REQUEST_URI + "/" + storeId+10 + "/" + productId, validStockRequestJson3Items , HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {
        });
        assertNotNull("Response shouldn't be null", stockReponse);
    }

    @Test
    @FlywayTest
    public void testSetStockBadProduct() {
        BaseResponse stockReponse = sendRequest(getBasePath() + STOCK_REQUEST_URI + "/" + storeId + "/" + productId+10, validStockRequestJson3Items , HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {
        });
        assertNotNull("Response shouldn't be null", stockReponse);
    }

    @Test
    @FlywayTest
    public void testSetStockBadRequest() {
        BaseResponse stockReponse = sendRequest(getBasePath() + STOCK_REQUEST_URI + "/" + storeId + "/" + productId, invalidStockRequestJson , HttpMethod.POST, HttpStatus.PRECONDITION_FAILED, new TypeReference<BaseResponse>() {
        });
        assertNotNull("Response shouldn't be null", stockReponse);
    }


}
