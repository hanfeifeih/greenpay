package com.esiran.greenpay.admin.controller.merchant;

import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/merchant")
public class AdminMerchantController {

    @GetMapping("/list")
    public String list(){
        return "admin/merchant/list";
    }
    @GetMapping("/list/{mchId}/edit")
    public String add(@PathVariable String mchId){
        return "admin/merchant/edit";
    }
    @GetMapping("/list/{mchId}/product/list")
    public String password(@PathVariable String mchId){
        return "admin/merchant/product/list";
    }


    @GetMapping("/list/{mchId}/product/edit")
    public String product(@PathVariable String mchId, @RequestParam String payTypeCode){
        return "admin/merchant/product/edit";
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
