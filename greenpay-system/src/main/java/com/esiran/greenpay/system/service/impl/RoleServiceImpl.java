package com.esiran.greenpay.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.system.entity.Role;
import com.esiran.greenpay.system.entity.UserRole;
import com.esiran.greenpay.system.entity.dot.UserRoleDto;
import com.esiran.greenpay.system.mapper.RoleMapper;
import com.esiran.greenpay.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 * <p>
 * 系统角色 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Role selectById(Long id) throws APIException {
        if (id <= 0) {
            throw new APIException("角色ID不正确","400");
        }
       return this.baseMapper.selectById(id);
    }

    @Override
    public boolean save(UserRoleDto roleDto) throws APIException {
        if (StringUtils.isBlank(roleDto.getName())) {
            throw new APIException("角色名称不能为空","400");
        }
        if (StringUtils.isBlank(roleDto.getRoleCode())) {
            throw new APIException("角色代码不能为空","400");
        }
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Role::getName,roleDto.getName());
        Role odl = this.baseMapper.selectOne(lambdaQueryWrapper);
        if (odl != null) {
            throw new APIException("角色已经存在","400");
        }
        Role role = modelMapper.map(roleDto, Role.class);
        return save(role);
    }

    @Override
    public boolean edit(UserRoleDto roleDto) throws APIException {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Role::getName, roleDto.getName());
        Role role = this.baseMapper.selectOne(lambdaQueryWrapper);
        if (role == null) {
            throw new APIException("未查询到信息","400");
        }
        role.setRoleCode(roleDto.getRoleCode());

        return updateById(role);
    }
}
