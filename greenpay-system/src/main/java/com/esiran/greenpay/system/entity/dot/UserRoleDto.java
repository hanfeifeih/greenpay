package com.esiran.greenpay.system.entity.dot;

import lombok.Data;

import java.util.List;

/**
 * @author han
 * @Package com.esiran.greenpay.system.entity.dot
 * @date 2020/4/17 18:16
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

    private List menuIds;
}
