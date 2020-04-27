package com.esiran.greenpay.admin.controller.system.menu;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.system.entity.Menu;
import com.esiran.greenpay.system.entity.dot.MenuDTO;
import com.esiran.greenpay.system.entity.vo.MenuInputVo;
import com.esiran.greenpay.system.entity.vo.MenuVo;
import com.esiran.greenpay.system.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity add(MenuInputVo menuInputVo) throws APIException{
        if (menuInputVo == null) {
            throw new APIException("参数不正确", HttpStatus.BAD_REQUEST + "");
        }
        Menu menu = modelMapper.map(menuInputVo, Menu.class);
        iMenuService.save(menu);

        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @PutMapping
    public ResponseEntity put( MenuInputVo menu) throws APIException{
        if (menu.getId() <= 0) {
            throw new APIException("权限ID不正确", "400");
        }
        //查找到
        Menu menu1 = iMenuService.getById(menu.getId());
        if (menu1 == null) {
            throw new APIException("未找到权限", HttpStatus.NOT_FOUND + "");
        }
        //更新
        Menu newMenu = modelMapper.map(menu, Menu.class);
        iMenuService.updateById(newMenu);
        //返回
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Menu menu = iMenuService.getById(id);
        if (menu == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(menu);
    }

    @GetMapping("/list")
    public ResponseEntity all(Page<Menu> page) {
        return iMenuService.selectAllUserMenue(page);
    }


    @DeleteMapping("/del")
    public ResponseEntity del(@RequestParam Integer id) throws Exception{
        if (id <= 0) {
            throw new Exception("菜单ID不正确");
        }
        Menu menuDTO = iMenuService.getById(id);
        if (menuDTO == null) {
            throw new Exception("菜单不存在");
        }
        iMenuService.removeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

}
