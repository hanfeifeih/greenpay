package com.esiran.greenpay.system.entity.dot;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author han
 */
@Data
public class UserDTO extends BaseMapperEntity {


    /**
     * 用户名
     */
    private String username;

    private String password;

    /**
     * 用户邮箱
     */
    private String email;


    private String roleNames;
}
