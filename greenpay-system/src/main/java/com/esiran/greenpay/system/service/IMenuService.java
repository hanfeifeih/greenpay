package com.esiran.greenpay.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
public interface IMenuService extends IService<Menu> {

    IPage<Menu> menuList(Page<Menu> menuPage);

}
