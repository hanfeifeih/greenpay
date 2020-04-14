package com.esiran.greenpay.admin.controller.merchant;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantProduct;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import com.esiran.greenpay.merchant.service.IMerchantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/merchants")
public class APIAdminMerchantController {
    private final IMerchantService merchantService;

    public APIAdminMerchantController(IMerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public IPage<Merchant> list(Page<Merchant> page){
        return merchantService.page(page);
    }
    @GetMapping("/{merchantId}/products")
    public List<MerchantProductDTO> product(@PathVariable Integer merchantId){
        return merchantService.selectMchProductById(merchantId);
    }
    @PostMapping("/add")
    public String add(){
        return "admin/merchant/add";
    }
}
