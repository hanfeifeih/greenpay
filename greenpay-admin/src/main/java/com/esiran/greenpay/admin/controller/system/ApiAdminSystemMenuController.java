package com.esiran.greenpay.admin.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.Menu;
import com.esiran.greenpay.system.entity.dot.MenuDTO;
import com.esiran.greenpay.system.service.IMenuService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author han
 * @Package com.esiran.greenpay.admin.controller.system
 * @date 2020/4/18 10:25
 */
@RestController
@RequestMapping("/admin/api/v1/system/menu")
public class ApiAdminSystemMenuController {

    private final static ModelMapper modelMapper = new ModelMapper();
    private IMenuService iMenuService;

    public ApiAdminSystemMenuController(IMenuService iMenuService) {
        this.iMenuService = iMenuService;
    }

    @GetMapping
    public IPage<MenuDTO> list(@RequestParam(required =false , defaultValue = "1") Integer current,
                               @RequestParam(required = false,defaultValue = "10") Integer size){
        IPage<Menu> menuIPage = iMenuService.menuList(new Page<>(current,size));

        IPage<MenuDTO> maps = modelMapper.map(menuIPage, new TypeToken<IPage<MenuDTO>>() {
        }.getType());

        return maps;
    }
}
