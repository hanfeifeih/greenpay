package com.esiran.greenpay.admin.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/v1/system/users")
public class APIAdminSystemUserController {

    private final IUserService userService;

    public APIAdminSystemUserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public IPage<User> list(
            @RequestParam(required = false,defaultValue = "1") Integer current,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        return userService.page(new Page<>(current,size));
    }
}
