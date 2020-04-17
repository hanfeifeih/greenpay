package com.esiran.greenpay.admin.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/system/role")
public class AdminSystemRoleController {
    @GetMapping("/list")
    public String list(){
        return "admin/system/role/list";
    }
//    @GetMapping("/add")
//    public String add(){
//        return "admin/merchant/add";
//    }

}
