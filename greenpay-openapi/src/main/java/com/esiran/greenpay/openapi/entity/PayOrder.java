package com.esiran.greenpay.openapi.entity;

import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.OrderDetail;
import lombok.Data;

@Data
public class PayOrder {
    private Order order;
    private OrderDetail orderDetail;
}
