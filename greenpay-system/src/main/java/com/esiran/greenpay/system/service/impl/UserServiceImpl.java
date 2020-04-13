package com.esiran.greenpay.system.service.impl;

import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.mapper.UserMapper;
import com.esiran.greenpay.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
