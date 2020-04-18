package com.esiran.greenpay.system.entity.dot;

import lombok.Data;

import java.util.List;

/**
 * @author han
 */
@Data
public class UserRoleDto {

    private Long id;


    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String roleCodes;

}
