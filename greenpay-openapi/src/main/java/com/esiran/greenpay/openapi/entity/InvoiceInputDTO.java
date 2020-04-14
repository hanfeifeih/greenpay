package com.esiran.greenpay.openapi.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class InvoiceInputDTO {
    /**
     * 应用ID
     */
    @NotBlank(message = "应用ID不能为空")
    private String appId;
    /**
     * 商户订单号
     */
    @NotBlank(message = "商户订单号不能为空")
    private String outOrderNo;
    /**
     * 订单金额（单位：分），必须大于0
     */
    @Min(value = 1,message = "订单金额（单位：分）必须大于0")
    @NotNull(message = "订单金额不能为空")
    private Integer amount;
    /**
     * 支付渠道
     */
    @NotBlank(message = "支付渠道不能为空")
    private String channel;

    /**
     * 商品标题
     */
    @NotBlank(message = "商品标题不能为空")
    private String subject;
    /**
     * 渠道额外参数
     */
    private Map<String,Object> channelExtra;
    /**
     * 商品描述信息
     */
    private String body;
    /**
     * 客户端地址
     */
    private String clientIp;
    /**
     * 回调地址
     */
//    @NotBlank(message = "回调地址不能为空")
    private String notifyUrl;

}
