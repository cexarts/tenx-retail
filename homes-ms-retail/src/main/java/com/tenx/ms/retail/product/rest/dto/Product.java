package com.tenx.ms.retail.product.rest.dto;

import com.tenx.ms.commons.validation.constraints.DollarAmount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel("Product")
@Data
public class Product {

    @ApiModelProperty(value = "Product Id", required = true)
    private Long productId;

    @ApiModelProperty(value = "Store that sells the product", required = true)
    private Long storeId;

    @NotNull
    @ApiModelProperty(value = "Product Name", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(value = "Product Description", required = true)
    private String description;

    @NotNull
    @Length(min=5, max=10, message = "Sku minimun size is 5 characters and maximun size 10 characters")
    @ApiModelProperty(value = "Product SKU", required = true)
    private String sku;

    @DollarAmount
    @NotNull
    @ApiModelProperty(value = "Product price", required = true)
    private BigDecimal price;
}
