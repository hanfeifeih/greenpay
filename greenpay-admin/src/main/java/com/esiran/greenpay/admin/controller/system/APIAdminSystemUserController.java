package com.esiran.greenpay.admin.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.service.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/system/users")
public class APIAdminSystemUserController {

    private static ModelMapper modelMapper = new ModelMapper();
    private final IUserService userService;

    public APIAdminSystemUserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public IPage<UserDTO> list(
            @RequestParam(required = false,defaultValue = "1") Integer current,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        Page<User> page = userService.page(new Page<>(current, size));
        Page<UserDTO> userDtos = modelMapper.map(page,new TypeToken<Page<UserDTO>>(){}.getType());
        return userDtos;
    }
}
