package com.tenx.ms.retail.order.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.rest.dto.OrderResponse;
import com.tenx.ms.retail.order.services.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "order", description = "Order Management API")
@RestController("orderControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@ApiParam(name = "storeId", value = "Store Id") @PathVariable() Long storeId,
                                               @Validated @RequestBody Order order) {
        order.setStoreId(storeId);
        Order orderCreated = orderService.createOrder(order);
        return new OrderResponse(orderCreated.getOrderId(),orderCreated.getStatus());
    }

    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(IllegalArgumentException.class)
    protected void handleUpdateViolationException(IllegalArgumentException ex,
                                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(EntityNotFoundException.class)
    protected void handleEntityNotFoundViolationException(EntityNotFoundException ex,
                                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage());
    }
}
