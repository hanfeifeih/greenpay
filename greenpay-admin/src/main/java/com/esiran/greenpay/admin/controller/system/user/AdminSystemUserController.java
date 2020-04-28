package com.esiran.greenpay.admin.controller.system.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.esiran.greenpay.common.entity.APIError;
import com.esiran.greenpay.common.entity.APIException;
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
    public String edit(HttpSession httpSession, ModelMap modelMap,@PathVariable Integer userId) throws APIException {
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
                userInputDto.getUsername().length()<2) {

              throw new Exception("用户名格式不正确");
        }
        if (StringUtils.isBlank(userInputDto.getEmail())) {
            throw new Exception("用户名或Email为空");
        }
        if (StringUtils.isBlank(userInputDto.getPassword())) {
            throw new Exception("用户名密码格式不正确");
        }
        User user = userService.getById(userId);
        LambdaQueryWrapper<User> queryWrapper ;
        if (!user.getEmail().equals(userInputDto.getEmail())){
            queryWrapper = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<User> eq = queryWrapper.like(User::getEmail, userInputDto.getEmail());
            User u = userService.getOne(eq);
            if (u != null ) {
                throw new Exception("邮箱已经存在");
            }
        }
        if (!user.getUsername().equals(userInputDto.getUsername())){
            queryWrapper = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<User> eq = queryWrapper.like(User::getUsername, userInputDto.getUsername());
            User u = userService.getOne(eq);
            if (u != null ) {
                throw new Exception("用户名已存在");
            }
        }

        user.setUsername(userInputDto.getUsername());
        user.setEmail(userInputDto.getEmail());
        user.setPassword(userInputDto.getPassword());
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



}
