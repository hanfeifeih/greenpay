package com.esiran.greenpay.system.entity.dot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author han
 */
@Data
public class UserDTO {


    /**
     * 用户名
     */
    private String username;



    /**
     * 用户邮箱
     */
    private String email;
}
