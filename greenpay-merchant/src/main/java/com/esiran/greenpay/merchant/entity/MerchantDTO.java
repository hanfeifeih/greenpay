package com.esiran.greenpay.merchant.entity;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;



@Data
@ApiModel("Merchant")
public class MerchantDTO {
    @ApiModelProperty("商户ID")
    private Integer id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("商户名称")
    private String name;
    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("联系手机")
    private String phone;

    @ApiModelProperty("商户状态")
    private Boolean status;

}
