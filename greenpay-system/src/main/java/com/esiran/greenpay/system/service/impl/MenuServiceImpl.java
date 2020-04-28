package com.esiran.greenpay.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.Menu;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.dot.MenuDTO;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.entity.dot.UserInputDto;
import com.esiran.greenpay.system.entity.vo.MenuVo;
import com.esiran.greenpay.system.mapper.MenuMapper;
import com.esiran.greenpay.system.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    private static  final ModelMapper modelMap = new ModelMapper();

    @Override
    public ResponseEntity selectAllUserMenue(Page<Menu> iPage) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        IPage<MenuVo> menuIPage = this.baseMapper.selectMenu(iPage, queryWrapper );
        return ResponseEntity.status(HttpStatus.OK).body(menuIPage.getRecords());
    }

    @Override
    public ResponseEntity selectMenu(Integer id) {
        QueryWrapper<MenuVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_user.id", id);
        queryWrapper.eq("parent_id", 0);
        MenuVo menuVo = this.baseMapper.selectMenu(queryWrapper);
        menuVo.setTitles(buildMenuRoles(menuVo.getMenus()));

        return ResponseEntity.status(HttpStatus.OK).body(menuVo);
    }

    @Override
    public ResponseEntity selectMenuAll() {
        QueryWrapper<MenuVo> queryWrapper = new QueryWrapper<>();
        MenuVo menuVo = this.baseMapper.selectMenu(queryWrapper);
        menuVo.setTitles(buildMenuRoles(menuVo.getMenus()));

        return ResponseEntity.status(HttpStatus.OK).body(menuVo);
    }

    private String buildMenuRoles(List<Menu> menus) {
        List<String> strings = menus.stream().map(item -> item.getTitle()).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            stringBuilder.append(strings.get(i)).append(i < strings.size()-1 ? "," : "");
        }
        return stringBuilder.toString();
    }

    @Override
    public IPage<Menu> menuList(Page<Menu> menuPage) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        Page<Menu> menuPage1 = this.baseMapper.selectPage(menuPage, queryWrapper);
        return menuPage1;
    }


    @Override
    public MenuDTO selectMenuById(Long userId) throws ApiException {
        Menu user = getById(userId);
        if (user == null) {
            throw new ApiException("菜单不存在");
        }
        return modelMap.map(user, MenuDTO.class);
    }

    @Override
    public void addMenu(MenuDTO menuDTO) throws Exception{
        Menu menu = modelMap.map(menuDTO,Menu.class);
        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Menu::getTitle, menu.getTitle());
        Menu oldMenu = getOne(lambdaQueryWrapper);
        if (oldMenu != null) {
            throw new Exception("菜单已经存在");
        }
        save(menu);
    }



}
