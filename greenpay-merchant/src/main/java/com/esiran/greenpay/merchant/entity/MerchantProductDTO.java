package com.esiran.greenpay.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.esiran.greenpay.common.entity.BaseMapperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 商户产品
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Data
public class MerchantProductDTO {

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 支付类型编码
     */
    private String payTypeCode;
    /**
     * 支付类型名称
     */
    private String payTypeName;

    /**
     * 支付产品ID
     */
    private Integer productId;
    /**
     * 支付产品名称
     */
    private String productName;

    /**
     * 通道费率
     */
    private BigDecimal rate;

    /**
     * 状态（0：关闭，1：开启）
     */
    private Boolean status;

}
