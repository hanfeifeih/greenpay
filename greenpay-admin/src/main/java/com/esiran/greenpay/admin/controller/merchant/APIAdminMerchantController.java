package com.esiran.greenpay.admin.controller.merchant;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.service.IMerchantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/v1/merchants")
public class APIAdminMerchantController {
    private final IMerchantService merchantService;

    public APIAdminMerchantController(IMerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public IPage<Merchant> list(){
//        merchantService.page()
        return merchantService.page(new Page<>(0,10));
    }
    @PostMapping("/add")
    public String add(){
        return "admin/merchant/add";
    }
}
