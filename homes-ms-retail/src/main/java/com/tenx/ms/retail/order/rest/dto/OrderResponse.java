package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.retail.order.domain.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel("Order")
@Data
@AllArgsConstructor
public class OrderResponse {

    @ApiModelProperty(value = "Order Id", required = true)
    private long orderId;

    @ApiModelProperty(value = "Order Status", required = true)
    private OrderStatus status;
}
