package com.esiran.greenpay.admin.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/system/menu")
public class AdminSystemMenuController {
    @GetMapping("/list")
    public String list(){
        return "admin/system/menu/list";
    }
//    @PostMapping("/add")
//    public String add(){
//        return "admin/merchant/add";
//    }
}
