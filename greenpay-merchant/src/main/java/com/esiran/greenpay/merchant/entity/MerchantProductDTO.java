package com.esiran.greenpay.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.esiran.greenpay.common.entity.BaseMapperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("MerchantProduct")
public class MerchantProductDTO {

    @ApiModelProperty("商户ID")
    private Integer merchantId;

    @ApiModelProperty("支付类型编码")
    private String payTypeCode;

    @ApiModelProperty("支付类型名称")
    private String payTypeName;

    @ApiModelProperty("支付产品ID")
    private Integer productId;

    @ApiModelProperty("支付产品名称")
    private String productName;

    @ApiModelProperty("通道费率")
    private BigDecimal rate;
    @ApiModelProperty("状态")
    private Boolean status;

}
