package com.esiran.greenpay.admin.controller.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/pay/type")
public class AdminPayTypeController {
    @GetMapping("/list")
    public String list(){
        return "admin/pay/type/list";
    }
    @GetMapping("/list/add")
    public String add(){
        return "admin/pay/type/add";
    }
    @GetMapping("/list/{payTypeCode}/edit")
    public String edit(@PathVariable String payTypeCode){
        return "admin/pay/type/edit";
    }
}
