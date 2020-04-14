package com.esiran.greenpay.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.esiran.greenpay.common.entity.BaseMapperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 支付订单
 * </p>
 *
 * @author Militch
 * @since 2020-04-14
 */
@Data
public class OrderDTO  {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 交易流水号
     */
    private String orderSn;

    /**
     * 商户ID
     */
    private Integer mchId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 商品标题
     */
    private String subject;

    /**
     * 商户订单号
     */
    private String outOrderNo;

    /**
     * 订单金额（单位：分）
     */
    private Integer amount;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 客户端IP
     */
    private Integer clientIp;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 跳转地址
     */
    private String redirectUrl;

    /**
     * 订单状态（0：待付款，2：已支付，3：订单完成，-1：交易取消，-2：交易失败）
     */
    private Boolean status;

    /**
     * 支付时间
     */
    private LocalDateTime paidAt;

    /**
     * 支付类型编码
     */
    private String payTypeCode;

    /**
     * 支付产品ID
     */
    private Integer payProductId;

    /**
     * 支付通道ID
     */
    private Integer payPassageId;

    /**
     * 支付通道子账户ID
     */
    private Integer payPassageAccId;

    /**
     * 支付接口ID
     */
    private Integer payInterfaceId;

    /**
     * 支付接口请求参数
     */
    private String payInterfaceParams;

    /**
     * 上游订单编号
     */
    private String upstreamOrderNo;

    /**
     * 上游扩展参数
     */
    private String upstreamExtra;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

}
