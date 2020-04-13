package com.esiran.greenpay.admin.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/system/user")
public class AdminSystemUserController {
    @GetMapping("/list")
    public String index(){
        return "admin/system/user/list";
    }
}
