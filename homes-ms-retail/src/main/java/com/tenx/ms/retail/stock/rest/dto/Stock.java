package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("Stock")
public class Stock {

    @ApiModelProperty(name = "Product Id", required = true)
    private Long productId;

    @ApiModelProperty(name = "Store Id", required = true)
    private Long storeId;

    @NotNull
    @ApiModelProperty(name = "Product count", required = true)
    private Long count;


}
