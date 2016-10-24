package com.tenx.ms.retail.store.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.Store;
import com.tenx.ms.retail.store.services.StoreService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "stores", description = "Store Management API")
@RestController("storeControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "Find store by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get Store Id Success"),
                    @ApiResponse(code = 404, message = "Store does not exist"),
                    @ApiResponse(code = 500, message = "Internal server error"),
            }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.GET)
    public Store getStoreById(@ApiParam(name = "storeId", value = "Store Id") @PathVariable() Long id) {
        return storeService.getStoreById(id);
    }

    @ApiOperation(value = "Find all stores")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get All Stores Success"),
                    @ApiResponse(code = 404, message = "Stores not found"),
                    @ApiResponse(code = 500, message = "Internal server error"),
            }
    )
    @RequestMapping(method = RequestMethod.GET)
    public List<Store> getAllStores()  {
        return storeService.getStores();
    }

    @ApiOperation(value = "Create a store")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Create store Success"),
                    @ApiResponse(code = 404, message = "URL not found"),
                    @ApiResponse(code = 500, message = "Internal server error"),
            }
    )
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceCreated<Long> createStore(@Validated @RequestBody Store store) {
        return new ResourceCreated<>(storeService.createStore(store));
    }

}
