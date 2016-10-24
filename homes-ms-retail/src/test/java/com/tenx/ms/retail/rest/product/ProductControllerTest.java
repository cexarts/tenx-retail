package com.tenx.ms.retail.rest.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.product.rest.dto.Product;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RetailServiceApp.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class ProductControllerTest extends BaseTest {

    private Long storeId;

    @Before
    public void createStore() {
        ResourceCreated<Long> resourceCreated = createStore(validStoreRequestJson, HttpStatus.CREATED);
        storeId = resourceCreated.getId();
    }

    @Test
    @FlywayTest
    public void testGetProductsEmpty() {
        List<Product> products = sendRequest(getBasePath() + PRODUCTS_REQUEST_URI + "/store/" + storeId, "", HttpMethod.GET, HttpStatus.OK, new TypeReference<List<Product>>() {
        });
        assertNotNull("Response shouldn't be null", products);
        assertTrue("Products should be Empty", products.size() == 0);
    }

    @Test
    @FlywayTest
    public void testCreateValidProduct() {
        ResourceCreated<Long> productResponse = sendRequest(getBasePath()+PRODUCTS_REQUEST_URI+"/"+storeId,validProductJson, HttpMethod.POST,  HttpStatus.CREATED,  new TypeReference<ResourceCreated<Long>>(){});
        assertNotNull("Response shouldn't be null", productResponse);
        assertTrue("Product Id should exist", productResponse.getId() > 0 );
    }

    @Test
    @FlywayTest
    public void testGetProductById() {
        ResourceCreated<Long> created = createProduct(storeId,validProductJson,HttpStatus.CREATED);
        Product product = sendRequest(getBasePath() + PRODUCTS_REQUEST_URI + "/" + created.getId(), "", HttpMethod.GET, HttpStatus.OK, new TypeReference<Product>() {});
        assertNotNull("Response shouldn't be null", product);
        assertTrue("Product Id should exist", created.getId().equals(product.getProductId()));
    }

    @Test
    @FlywayTest
    public void testCreateInvalidProductSKU() {
        ResourceCreated<Long> productResponse = sendRequest(getBasePath()+PRODUCTS_REQUEST_URI+"/"+storeId,notvalidProductSKUJson, HttpMethod.POST,  HttpStatus.PRECONDITION_FAILED,  new TypeReference<ResourceCreated<Long>>(){});
        assertNotNull("Response shouldn't be null", productResponse);
        assertTrue("Product Id should not exist", productResponse.getId() == null );
    }

    @Test
    @FlywayTest
    public void testCreateInvalidProductAmount() {
        ResourceCreated<Long> productResponse = sendRequest(getBasePath()+PRODUCTS_REQUEST_URI+"/"+storeId,notvalidProductAmountJson, HttpMethod.POST,  HttpStatus.PRECONDITION_FAILED,  new TypeReference<ResourceCreated<Long>>(){});
        assertNotNull("Response shouldn't be null", productResponse);
        assertTrue("Product Id should not exist", productResponse.getId() == null );
    }

    @Test
    @FlywayTest
    public void testGetProductsByStore() {
        ResourceCreated<Long> productResponse1 = createProduct(storeId,validProductJson,HttpStatus.CREATED);
        ResourceCreated<Long> productResponse2 = createProduct(storeId,validProductJson2,HttpStatus.CREATED);
        Set<Long> idSet = new HashSet<>();
        idSet.add(productResponse1.getId());
        idSet.add(productResponse2.getId());
        List<Product> products = sendRequest(getBasePath() + PRODUCTS_REQUEST_URI + "/store/" + storeId, "", HttpMethod.GET, HttpStatus.OK, new TypeReference<List<Product>>() {});
        assertNotNull("Response shouldn't be null", products);
        assertTrue("Number of products corresponds", products.size() == idSet.size());
        assertTrue("Products correspond to created ones", checkProducts(idSet, products));
    }

    private boolean checkProducts(Set<Long> idSet, List<Product> products) {
        for (Product product: products) {
            if (!idSet.contains(product.getProductId())) {
                return false;
            }
            idSet.remove(product.getProductId());
        }
        return idSet.size() == 0;
    }
}
