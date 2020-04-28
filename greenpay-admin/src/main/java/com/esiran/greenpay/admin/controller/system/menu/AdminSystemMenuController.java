package com.esiran.greenpay.admin.controller.system.menu;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.entity.APIError;
import com.esiran.greenpay.system.entity.Menu;
import com.esiran.greenpay.system.entity.dot.MenuDTO;
import com.esiran.greenpay.system.entity.vo.MenuVo;
import com.esiran.greenpay.system.service.IMenuService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/system/menu")
public class AdminSystemMenuController {

    private IMenuService iMenuService;

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public AdminSystemMenuController(IMenuService iMenuService) {
        this.iMenuService = iMenuService;
    }

    @GetMapping("/list")
    @ResponseBody
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView("admin/system/menu/permList");
        List<Menu> list = iMenuService.list();
        modelAndView.addObject("permList",list);
        modelAndView.addObject("msg", "ok");
        return modelAndView;
    }

    @GetMapping("/list/{menuId}/edit")
    public String edit(HttpSession httpSession, ModelMap modelMap, @PathVariable Long menuId) {
        List<APIError> apiErrors = (List<APIError>) httpSession.getAttribute("errors");
        modelMap.addAttribute("errors", apiErrors);
        httpSession.removeAttribute("errors");
        MenuDTO menuDTO = iMenuService.selectMenuById(menuId);
        modelMap.addAttribute("menu", menuDTO);
        return "admin/system/menu/edit";
    }


    @PostMapping("/list/{menuId}/edit")
    public String edit(@PathVariable Integer menuId, MenuDTO menuDTO) throws Exception {

        if (StringUtils.isBlank(menuDTO.getTitle())){
            throw new Exception("标题不能为空");
        }
        if (StringUtils.isBlank(menuDTO.getMark())){
            throw new Exception("标识不能为空");
        }
        if (menuDTO.getType()<=0){
            throw new Exception("菜单类型不正确");
        }
        if (StringUtils.isBlank(menuDTO.getPath())){
            throw new Exception("菜单路由不能为空");
        }
        if (menuDTO.getParentId()<=0){
            throw new Exception("上级ID不正确");
        }
        Menu menu = iMenuService.getById(menuId);
        menu.setTitle(menuDTO.getTitle());
        menu.setMark(menuDTO.getMark());
        menu.setType(menuDTO.getType());
        menu.setPath(menuDTO.getPath());
        menu.setSorts(menuDTO.getSorts());
        menu.setParentId(menuDTO.getParentId());
        iMenuService.updateById(menu);
        return "redirect:/admin/system/menu/list";
    }



    @GetMapping("/add")
    public String add(HttpSession httpSession, ModelMap modelMap) {
        List<APIError> apiErrors = (List<APIError>) httpSession.getAttribute("errors");
        modelMap.addAttribute("errors", apiErrors);
        httpSession.removeAttribute("errors");
        return "admin/system/menu/add";
    }


    @PostMapping("/add")
    public String add(@Valid MenuDTO menuDTO) throws Exception{
        iMenuService.addMenu(menuDTO);
        return "redirect:/admin/system/menu/list";
    }
}
