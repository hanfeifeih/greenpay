package com.esiran.greenpay.merchant.entity;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
public class MerchantInputDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空")
    private String name;

    /**
     * 电子邮箱
     */
    @NotBlank(message = "电子邮箱不能为空")
    private String email;

    /**
     * 联系手机
     */
    private String phone;
    /**
     * 商户状态（0：禁用，1：启用）
     */
    private Boolean status;
    /**
     * 商户登录密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
