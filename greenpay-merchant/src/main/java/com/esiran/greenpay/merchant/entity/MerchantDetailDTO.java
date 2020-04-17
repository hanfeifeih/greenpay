package com.esiran.greenpay.merchant.entity;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@EqualsAndHashCode(callSuper = true)
@Data
public class MerchantDetailDTO extends BaseMapperEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 联系手机
     */
    private String phone;
    /**
     * 商户状态（0：禁用，1：启用）
     */
    private Boolean status;
    private ApiConfigDTO apiConfig;
    private PayAccountDTO payAccount;
    private PrepaidAccountDTO prepaidAccount;
}
