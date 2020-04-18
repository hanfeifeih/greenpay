package com.esiran.greenpay.admin.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.Role;
import com.esiran.greenpay.system.entity.dot.UserRoleDto;
import com.esiran.greenpay.system.service.IRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author han
 */

@RestController
@RequestMapping("/admin/api/v1/system/roles")
public class APIAdminSystemRoleController {
    private static ModelMapper modelMapper = new ModelMapper();

    private final IRoleService roleService;

    public APIAdminSystemRoleController(IRoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping
    public IPage<Role> list(
            @RequestParam(required = false,defaultValue = "1") Integer current,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        return roleService.page(new Page<>(current,size));
    }

    @GetMapping("/{id}")
    public UserRoleDto get(@PathVariable("id") Long userId){
        return null;
//        return roleService.selectUserRoleById(userId);
    }

}
