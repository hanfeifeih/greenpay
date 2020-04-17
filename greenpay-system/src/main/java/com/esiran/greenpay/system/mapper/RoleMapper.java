package com.esiran.greenpay.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.esiran.greenpay.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.esiran.greenpay.system.entity.UserRole;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.entity.dot.UserRoleDto;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统角色 Mapper 接口
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */

public interface RoleMapper extends BaseMapper<Role> {



}
