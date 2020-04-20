package com.esiran.greenpay.admin.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/system/users")
@Api(tags = "用户管理")
public class APIAdminSystemUserController {

    private static ModelMapper modelMapper = new ModelMapper();
    private final IUserService userService;

    public APIAdminSystemUserController(IUserService userService) {
        this.userService = userService;
    }

    @ApiOperation("查询所有用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10")
    })

    @GetMapping
    public IPage<UserDTO> list(
            @RequestParam(required = false, defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        Page<User> page = userService.page(new Page<>(current, size));
        Page<UserDTO> userDtos = modelMapper.map(page, new TypeToken<Page<UserDTO>>() {
        }.getType());
        return userDtos;
    }


    @DeleteMapping("/del")
    public boolean del(@RequestParam Integer id) throws Exception{
        if (id <= 0) {
            throw new Exception("用户ID不正确");
        }
        UserDTO userDTO = userService.selectUserById(id);
        if (userDTO == null) {
            throw new Exception("用户不存在");
        }
        userService.removeById(id);
        return true;
    }
}
