package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("Store")
public class Store {

    @ApiModelProperty(name="Store id", required = true)
    private Long storeId;

    @NotNull
    @ApiModelProperty(name="Store name", required = true)
    private String name;
}
