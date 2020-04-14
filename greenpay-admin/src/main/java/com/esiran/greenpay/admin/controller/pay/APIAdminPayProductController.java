package com.esiran.greenpay.admin.controller.pay;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.pay.entity.Product;
import com.esiran.greenpay.pay.entity.Type;
import com.esiran.greenpay.pay.service.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/pay/products")
public class APIAdminPayProductController {
    private final IProductService productService;

    public APIAdminPayProductController(IProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public IPage<Product> page(Page<Product> page){
        return productService.page(page);
    }

    @PostMapping("/add")
    public String add(){
        return "admin/merchant/add";
    }
}
