package com.esiran.greenpay.admin.controller.system.menu;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.Menu;
import com.esiran.greenpay.system.entity.dot.MenuDTO;
import com.esiran.greenpay.system.entity.vo.MenuVo;
import com.esiran.greenpay.system.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author han
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/api/v1/system/menus")
public class ApiAdminSystemMenuController {

    private final static ModelMapper modelMapper = new ModelMapper();
    private IMenuService iMenuService;

    public ApiAdminSystemMenuController(IMenuService iMenuService) {
        this.iMenuService = iMenuService;
    }

    @ApiOperation("查询所有菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(name = "size",value = "每页个数 ",defaultValue = "10")
    })
    @GetMapping
    public IPage<MenuDTO> list(@RequestParam(required = false, defaultValue = "1") Integer current,
                               @RequestParam(required = false, defaultValue = "10") Integer size) {

        IPage<Menu> menuIPage = iMenuService.menuList(new Page<>(current, size));

        IPage<MenuDTO> maps = modelMapper.map(menuIPage, new TypeToken<IPage<MenuDTO>>() {
        }.getType());

        return maps;
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        return iMenuService.selectMenuById(id);
    }

    @GetMapping("/list")
    public ResponseEntity all(Page<Menu> page) {
        return iMenuService.selectAllUserMenue(page);
    }


    @DeleteMapping("/del")
    public boolean del(@RequestParam Integer id) throws Exception{
        if (id <= 0) {
            throw new Exception("用户ID不正确");
        }
        MenuDTO menuDTO = iMenuService.selectMenuById(id);
        if (menuDTO == null) {
            throw new Exception("用户不存在");
        }
        iMenuService.removeById(id);
        return true;
    }

}
