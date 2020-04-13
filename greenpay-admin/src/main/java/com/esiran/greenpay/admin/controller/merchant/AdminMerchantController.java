package com.esiran.greenpay.admin.controller.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/merchant")
public class AdminMerchantController {
    @GetMapping("/list")
    public String list(){
        return "admin/merchant/list";
    }
    @GetMapping("/list/{userId}/edit")
    public String add(@PathVariable String userId){
        return "admin/merchant/edit";
    }
    @GetMapping("/list/{userId}/password")
    public String password(@PathVariable String userId){
        return "admin/merchant/password";
    }
    @GetMapping("/list/{userId}/settle")
    public String settle(@PathVariable String userId){
        return "admin/merchant/settle";
    }
    @GetMapping("/add")
    public String add(){
        return "admin/merchant/add";
    }
}
