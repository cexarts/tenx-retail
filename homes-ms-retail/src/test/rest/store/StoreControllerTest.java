package rest.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import rest.BaseTest;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RetailServiceApp.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class StoreControllerTest extends BaseTest {


    @Test
    @FlywayTest
    public void testGetStoresEmpty() {
        List<Store> stores = sendRequest(getBasePath()+STORES_REQUEST_URI, "", HttpMethod.GET,  HttpStatus.OK,  new TypeReference<List<Store>>(){});
        assertNotNull("Response shouldn't be null", stores);
    }

    @Test
    @FlywayTest
    public void testGetStoresNotEmpty() {
        createStore(validStoreRequestJson, HttpStatus.CREATED);
        List<Store> stores = sendRequest(getBasePath()+STORES_REQUEST_URI, "", HttpMethod.GET,  HttpStatus.OK,  new TypeReference<List<Store>>(){});
        assertNotNull("Response shouldn't be null", stores);
        assertTrue("Id should exist", stores.size() > 0);
    }

    @Test
    @FlywayTest
    public void testCreateStoreGoodRequest() {
        ResourceCreated<Long> resourceCreated = createStore(validStoreRequestJson,HttpStatus.CREATED);
        assertNotNull("Response shouldn't be null", resourceCreated);
        assertTrue("Id should exist", resourceCreated.getId() > 0);

    }

    @Test
    @FlywayTest
    public void testCreateStoreBadRequest() {
        ResourceCreated<Long> resourceCreated = createStore(notValidStoreRequestJson,HttpStatus.PRECONDITION_FAILED);
        assertTrue("Id should not exist", resourceCreated.getId() == null);
    }

    @Test
    @FlywayTest
    public void testFindExistingStore() {
        ResourceCreated<Long> resourceCreated = createStore(validStoreRequestJson,HttpStatus.CREATED);
        Long id = resourceCreated.getId();
        Store store = sendRequest(getBasePath()+STORES_REQUEST_URI+"/"+id, "", HttpMethod.GET,  HttpStatus.OK,  new TypeReference<Store>(){});
        assertNotNull("Response shouldn't be null", store);
        assertTrue("Is is the same", store.getStoreId().equals(id));
    }


}
