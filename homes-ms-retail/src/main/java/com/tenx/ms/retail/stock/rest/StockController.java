package com.tenx.ms.retail.stock.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.services.ProductService;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.services.StockService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "stock", description = "Stock Management API")
@RestController("stockControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stock")
public class StockController {

    @Autowired
    private StockService stockService;


    @ApiOperation(value = "Edit a Stock")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Edit Success"),
                    @ApiResponse(code = 412, message = "Product or store not found"),
                    @ApiResponse(code = 500, message = "Internal server error"),
            }
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceCreated<Long> editStock(@ApiParam(name = "storeId", value = "Store Id") @PathVariable() Long storeId,
                                               @ApiParam(name = "productId", value = "Product Id") @PathVariable() Long productId,
                                               @Validated @RequestBody Stock stock) {
        stock.setProductId(productId);
        stock.setStoreId(storeId);
        stockService.editStock(stock);
        return new ResourceCreated<>();
    }

    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(EntityNotFoundException.class)
    protected void handleUpdateViolationException(EntityNotFoundException ex,
                                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage());
    }

}

