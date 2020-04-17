package com.esiran.greenpay.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.system.entity.dot.UserRoleDto;

/**
 * <p>
 * 系统用户角色 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
public interface IUserRoleService extends IService<UserRole> {
    IPage<UserRoleDto> selectUserRoles(Page<UserRole> userVoPage);

    UserRoleDto selectUserRoleById(Long userId);
}
