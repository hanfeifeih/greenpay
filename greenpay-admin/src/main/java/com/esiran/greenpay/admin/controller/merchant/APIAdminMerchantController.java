package com.esiran.greenpay.admin.controller.merchant;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.util.ListUtil;
import com.esiran.greenpay.common.util.RSAUtil;
import com.esiran.greenpay.merchant.entity.ApiConfig;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.esiran.greenpay.merchant.service.IMerchantService;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/api/v1/merchants")
public class APIAdminMerchantController {
    private final IMerchantService merchantService;
    private final IApiConfigService apiConfigService;
    public APIAdminMerchantController(IMerchantService merchantService, IApiConfigService apiConfigService) {
        this.merchantService = merchantService;
        this.apiConfigService = apiConfigService;
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

    @PostMapping(value = "/{merchantId}/mch_pub_key",produces = "text/plain")
    public void publicKey(@PathVariable String merchantId, @RequestBody String content) throws Exception {
        LambdaQueryWrapper<ApiConfig> queryWrapper = new QueryWrapper<ApiConfig>()
                .lambda().eq(ApiConfig::getMchId,merchantId);
        ApiConfig apiConfig = apiConfigService.getOne(queryWrapper);
        if (apiConfig == null){
            throw new Exception("商户不存在");
        }
        String publicKey = RSAUtil.resolvePublicKey(content);
        LambdaUpdateWrapper<ApiConfig> updateWrapper = new UpdateWrapper<ApiConfig>().lambda();
        updateWrapper.set(ApiConfig::getMchPubKey,publicKey)
                .eq(ApiConfig::getMchId,merchantId);
        apiConfigService.update(updateWrapper);
    }




}
