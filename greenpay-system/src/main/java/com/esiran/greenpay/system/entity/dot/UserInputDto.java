package com.esiran.greenpay.system.entity.dot;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author han
 */
@Data
@ApiModel("UserInput")
public class UserInputDto  extends BaseMapperEntity {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 16, message = "姓名长度不正确")
    private String username;


    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 16, message = "用户密码长度不正确")
    private String password;


    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty("用户角色")
    @NotBlank(message = "请选择正确的角色")
    private String roleIds;
}
