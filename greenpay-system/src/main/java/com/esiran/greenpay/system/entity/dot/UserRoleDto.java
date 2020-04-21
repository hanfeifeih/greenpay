package com.esiran.greenpay.system.entity.dot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author han
 */
@Data
@ApiModel("RoleDto")
public class UserRoleDto {

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;


}
