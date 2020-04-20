package com.esiran.greenpay.system.entity.dot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author han
 */
@Data
@ApiModel("UserInput")
public class UserInputDto {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;


    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    @NotBlank(message = "用户密码不能为空")
    private String password;



    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

}
