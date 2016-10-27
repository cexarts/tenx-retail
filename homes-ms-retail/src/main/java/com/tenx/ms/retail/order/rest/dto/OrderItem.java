package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("Product for order")
public class OrderItem {

    @NotNull
    private Long productId;

    @NotNull
    private Long count;

}
