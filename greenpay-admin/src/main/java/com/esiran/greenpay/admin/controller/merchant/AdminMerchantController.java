package com.esiran.greenpay.admin.controller.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/merchant")
public class AdminMerchantController {
    @GetMapping("/list")
    public String list(){
        return "admin/merchant/list";
    }
    @GetMapping("/add")
    public String add(){
        return "admin/merchant/add";
    }
}
