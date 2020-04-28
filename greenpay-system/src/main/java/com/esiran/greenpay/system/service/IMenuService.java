package com.esiran.greenpay.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.system.entity.dot.MenuDTO;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.entity.dot.UserInputDto;
import com.esiran.greenpay.system.entity.vo.MenuVo;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

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

    void addMenu(MenuDTO menuDTO) throws Exception;

    MenuDTO selectMenuById(Long userId) throws ApiException;

    ResponseEntity selectAllUserMenue(Page<Menu> iPage);

    ResponseEntity selectMenu(Integer id);

     ResponseEntity selectMenuAll();
}
