package com.esiran.greenpay.openapi.entity;

import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.OrderDetail;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.Data;
import me.chanjar.weixin.mp.api.WxMpService;

@Data
public class PayOrder {
    private Order order;
    private OrderDetail orderDetail;
    private WxMpService wxMpService;
    private WxPayService wxPayService;

}
