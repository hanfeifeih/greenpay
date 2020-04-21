package com.esiran.greenpay.admin.controller.system.user;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.esiran.greenpay.common.entity.APIError;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.dot.UserDTO;
import com.esiran.greenpay.system.entity.dot.UserInputDto;
import com.esiran.greenpay.system.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.PublicKey;
import java.util.List;

@Controller
@RequestMapping("/admin/system/user")
public class AdminSystemUserController {


    private final IUserService userService;

    public AdminSystemUserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String index() {
        return "admin/system/user/list";
    }


    @GetMapping("/list/{userId}/edit")
    public String edit(HttpSession httpSession, ModelMap modelMap,@PathVariable Integer userId) {
        List<APIError> apiErrors = (List<APIError>) httpSession.getAttribute("errors");
        modelMap.addAttribute("errors", apiErrors);
        httpSession.removeAttribute("errors");
        UserDTO user = userService.selectUserById(userId);
        modelMap.addAttribute("user", user);
        return "admin/system/user/edit";
    }


    @PostMapping("/list/{userId}/edit")
    public String edit(@PathVariable Integer userId, UserInputDto userInputDto) throws Exception {

        if (StringUtils.isBlank(userInputDto.getUsername()) ||
                StringUtils.isBlank(userInputDto.getEmail())) {

              throw new Exception("用户名或Email为空");
        }
        User user = userService.getById(userId);
        user.setUsername(userInputDto.getUsername());
        user.setEmail(userInputDto.getEmail());
        userService.updateById(user);
        return "redirect:/admin/system/user/list";
    }



    @GetMapping("/add")
    public String add(HttpSession httpSession, ModelMap modelMap) {
        List<APIError> apiErrors = (List<APIError>) httpSession.getAttribute("errors");
        modelMap.addAttribute("errors", apiErrors);
        httpSession.removeAttribute("errors");
        return "admin/system/user/add";
    }


    @PostMapping("/add")
    public String add(@Valid UserInputDto userInputDto) throws Exception{
        userService.addUser(userInputDto);
        return "redirect:/admin/system/user/list";
    }



}
