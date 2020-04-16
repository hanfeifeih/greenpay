package com.esiran.greenpay.admin.controller.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/pay/passage")
public class AdminPayPassageController {
    @GetMapping("/list")
    public String list(){
        return "admin/pay/passage/list";
    }
    @GetMapping("/list/add")
    public String add(){
        return "admin/pay/passage/add";
    }
    @GetMapping("/list/{passageId}/acc")
    public String listAcc(@PathVariable String passageId){
        return "admin/pay/passage/acc/list";
    }
    @GetMapping("/list/{passageId}/acc/add")
    public String addAcc(@PathVariable String passageId){
        return "admin/pay/passage/acc/add";
    }
    @GetMapping("/list/{passageId}/acc/{accId}/edit")
    public String editAcc(@PathVariable String passageId, @PathVariable String accId){
        return "admin/pay/passage/acc/edit";
    }
}
