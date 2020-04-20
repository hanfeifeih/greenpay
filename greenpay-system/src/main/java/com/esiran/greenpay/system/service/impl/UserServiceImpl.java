package com.esiran.greenpay.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.entity.dot.UserInputDto;
import com.esiran.greenpay.system.mapper.UserMapper;
import com.esiran.greenpay.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

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
    private static  final ModelMapper modelMap = new ModelMapper();

    @Override
    public void addUser(UserInputDto userInputDto) throws Exception{
        User user = modelMap.map(userInputDto,User.class);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername()).or()
        .eq(User::getEmail, user.getEmail());
        User oldUser = getOne(lambdaQueryWrapper);
        if (oldUser != null) {
            throw new Exception("用户名或邮箱已经存在");
        }
        save(user);
    }

    @Override
    public UserDTO selectUserById(Integer userId) throws ApiException {
        User user = getById(userId);
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        return modelMap.map(user, UserDTO.class);
    }
}
