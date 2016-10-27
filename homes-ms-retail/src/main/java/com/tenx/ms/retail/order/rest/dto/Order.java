package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.commons.validation.constraints.Email;
import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.order.domain.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@ApiModel("Order")
@Data
public class Order {

    @ApiModelProperty(value = "Order Id", required = true)
    private Long orderId;

    @ApiModelProperty(value = "Store Id", required = true)
    private Long storeId;

    @ApiModelProperty(value = "Buyer first name", required = true)
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain letters only")
    private String firstName;

    @ApiModelProperty(value = "Buyer last name", required = true)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain letters only")
    private String lastName;

    @NotNull
    @Email(message = "Must be a valid email address")
    @ApiModelProperty(value = "Buyer email", required = true)
    private String email;

    @ApiModelProperty(value = "Buyer phone", required = true)
    @PhoneNumber
    private String phone;

    @ApiModelProperty(value = "Order status", required = true)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ApiModelProperty(value = "Order date", required = true)
    private Timestamp orderDate;

    @Size(min = 1)
    @Valid
    @NotNull
    @ApiModelProperty(value = "Order product list", required = true)
    private List<OrderItem> products;

}
