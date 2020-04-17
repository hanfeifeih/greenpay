package com.esiran.greenpay.system.entity.dot;

import lombok.Data;

/**
 * @author han
 * @Package com.esiran.greenpay.system.entity.dot
 * @date 2020/4/17 18:07
 */
@Data
public class UserDTO {


    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;
}
