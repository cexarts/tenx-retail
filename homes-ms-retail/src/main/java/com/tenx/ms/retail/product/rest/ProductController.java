package com.tenx.ms.retail.product.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.services.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(value = "products", description = "Product Management API")
@RestController("productControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Find product by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get Product Id Success"),
                    @ApiResponse(code = 404, message = "Product does not exist"),
                    @ApiResponse(code = 500, message = "Internal server error"),
            }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.GET)
    public Product getProductById(@ApiParam(name = "productId", value = "Product Id") @PathVariable() Long id) {
        return productService.getProductById(id);
    }

    @ApiOperation(value = "Find all products")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get All products Success"),
                    @ApiResponse(code = 404, message = "Products not found"),
                    @ApiResponse(code = 500, message = "Internal server error"),
            }
    )
    @RequestMapping(value = {"/store/{id:\\d+}"} , method = RequestMethod.GET)
    public List<Product> getAllProductsByStore(@ApiParam(name = "storeId", value = "Store Id") @PathVariable() Long id) {
        return productService.getProducts(id);
    }

    @ApiOperation(value = "Create a product")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Create product Success"),
                    @ApiResponse(code = 404, message = "URL not found"),
                    @ApiResponse(code = 500, message = "Internal server error"),
            }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceCreated<Long> createProduct(@ApiParam(name = "storeId", value = "Store Id") @PathVariable() Long id, @Validated @RequestBody Product product) {
        product.setStoreId(id);
        return new ResourceCreated<>(productService.createProduct(product));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    protected void handleUpdateViolationException(EntityNotFoundException ex,
                                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }


}
