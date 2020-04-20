package com.esiran.greenpay.system.service;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.esiran.greenpay.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.entity.dot.UserInputDto;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
public interface IUserService extends IService<User> {


     void addUser(UserInputDto userInputDto) throws Exception;

    UserDTO selectUserById(Integer userId) throws ApiException;




}
