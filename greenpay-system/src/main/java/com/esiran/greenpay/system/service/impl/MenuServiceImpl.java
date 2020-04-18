package com.esiran.greenpay.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.Menu;
import com.esiran.greenpay.system.mapper.MenuMapper;
import com.esiran.greenpay.system.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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


    @Override
    public IPage<Menu> menuList(Page<Menu> menuPage) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        Page<Menu> menuPage1 = this.baseMapper.selectPage(menuPage, queryWrapper);
        return menuPage1;
    }
}
