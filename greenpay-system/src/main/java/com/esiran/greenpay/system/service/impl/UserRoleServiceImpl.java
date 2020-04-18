package com.esiran.greenpay.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.UserRole;
import com.esiran.greenpay.system.entity.dot.UserRoleDto;
import com.esiran.greenpay.system.mapper.UserRoleMapper;
import com.esiran.greenpay.system.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户角色 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Override
    public IPage<UserRoleDto> selectUserRoles(Page<UserRole> userVoPage) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        return this.baseMapper.selectRole(userVoPage,wrapper);
    }

    @Override
    public UserRoleDto selectUserRoleById(Long userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("system_user_role.user_id", userId);
        UserRoleDto userRoleDto = this.baseMapper.selectRolesById(wrapper);
        return userRoleDto;
    }
}
