package com.esiran.greenpay.admin.controller.system.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.UserRole;
import com.esiran.greenpay.system.entity.dot.MenuDTO;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.entity.dot.UserInputDto;
import com.esiran.greenpay.system.entity.vo.MenuVo;
import com.esiran.greenpay.system.service.IMenuService;
import com.esiran.greenpay.system.service.IUserRoleService;
import com.esiran.greenpay.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/system/users")
@Api(tags = "用户管理")
public class APIAdminSystemUserController {

    private static ModelMapper modelMapper = new ModelMapper();
    private final IUserService userService;
    private final IUserRoleService iUserRoleService;
    private final IMenuService iMenuService;

    public APIAdminSystemUserController(IUserService userService, IUserRoleService iUserRoleService, IMenuService iMenuService) {
        this.userService = userService;
        this.iUserRoleService = iUserRoleService;
        this.iMenuService = iMenuService;
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
        IPage<UserDTO> convert = page.convert(item -> modelMapper.map(item, UserDTO.class));

        for (UserDTO user : convert.getRecords()) {
            ResponseEntity responseEntity = iMenuService.selectMenu(user.getId());
            MenuVo menuVo = (MenuVo) responseEntity.getBody();
            user.setRoleNames(menuVo.getTitles());
        }

        return convert;
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


    @PostMapping
    @Transactional
    public ResponseEntity add(@Valid UserInputDto userInputDto) throws APIException {

        User user = userService.addUser(userInputDto);
        String[] split = userInputDto.getRoleIds().split(",");
        UserRole role = new UserRole();
        for (String s : split) {
            Integer id = Integer.valueOf(s);
            role.setUserId(user.getId());
            role.setRoleId(id);
            iUserRoleService.save(role);
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}
