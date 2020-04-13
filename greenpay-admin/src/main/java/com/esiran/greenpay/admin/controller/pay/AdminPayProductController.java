package com.esiran.greenpay.admin.controller.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/pay/product")
public class AdminPayProductController {
    @GetMapping("/list")
    public String list(){
        return "admin/pay/product/list";
    }
//    @GetMapping("/add")
//    public String add(){
//        return "admin/merchant/add";
//    }
}
