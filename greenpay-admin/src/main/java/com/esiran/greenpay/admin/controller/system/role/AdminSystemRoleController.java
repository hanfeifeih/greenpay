package com.esiran.greenpay.admin.controller.system.role;

import com.esiran.greenpay.common.entity.APIError;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.system.entity.Role;
import com.esiran.greenpay.system.entity.dot.UserRoleDto;
import com.esiran.greenpay.system.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/system/role")
public class AdminSystemRoleController {
    private IRoleService roleService;


    public AdminSystemRoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

//    @GetMapping("/list")
//    public String list() {
//        return "admin/system/role/list";
//    }

    /**
     * 跳转到角色列表
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView toPage() {
        ModelAndView modelAndView = new ModelAndView("admin/system/role/roleList");


        return modelAndView;
    }


    @GetMapping("/add")
    public String add(HttpSession httpSession, ModelMap modelMap) {
        List<APIError> apiErrors = (List<APIError>) httpSession.getAttribute("errors");
        if (!CollectionUtils.isEmpty(apiErrors)) {
                modelMap.addAttribute("errors", apiErrors);
                httpSession.removeAttribute("errors");
        }
        return "/admin/system/role/role";
    }


    @GetMapping("/list/edit/{id}")
    public String edit(@NotNull HttpSession httpSession, ModelMap modelMap, @PathVariable Long id) throws APIException{
        List<APIError> apiErrors = (List<APIError>) httpSession.getAttribute("errors");
        if (!CollectionUtils.isEmpty(apiErrors)) {
            modelMap.addAttribute("errors", apiErrors);
            httpSession.removeAttribute("errors");
        }
        Role role = roleService.selectById(id);
        modelMap.addAttribute("role", role);
        return "admin/system/role/roleUpdate";
    }

    @PostMapping("/list/edit/{id}")
    public String update(@PathVariable Long id, UserRoleDto roleDto) throws APIException{
        if (org.apache.commons.lang3.StringUtils.isBlank(roleDto.getName())) {
            throw new APIException("角色名称不能为空","400");
        }
        if (StringUtils.isBlank(roleDto.getRoleCode())) {
            throw new APIException("角色代码不能为空","400");
        }

        Role role = roleService.selectById(id);
        role.setName(roleDto.getName());
        role.setRoleCode(roleDto.getRoleCode());
        roleService.updateById(role);

        return "redirect:/admin/system/role/list";
    }



}
