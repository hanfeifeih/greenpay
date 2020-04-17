package com.esiran.greenpay.admin.controller.merchant;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.common.util.RSAUtil;
import com.esiran.greenpay.merchant.entity.*;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.esiran.greenpay.merchant.service.IMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/merchants")
@Api(tags = "商户管理")
public class APIAdminMerchantController {
    private final IMerchantService merchantService;
    private final IApiConfigService apiConfigService;
    public APIAdminMerchantController(IMerchantService merchantService, IApiConfigService apiConfigService) {
        this.merchantService = merchantService;
        this.apiConfigService = apiConfigService;
    }

    @ApiOperation("查询所有商户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="current",value="当前页码",defaultValue = "1"),
            @ApiImplicitParam(name="size",value="每页个数",defaultValue = "10"),
    })
    @GetMapping
    public IPage<MerchantDTO> list(
            @RequestParam(required = false,defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "10") Integer size){
        return merchantService.selectMerchantByPage(new Page<>(current,size));
    }


    @ApiOperation("查询指定商户的支付产品列表")
    @ApiImplicitParam(name="mchId", value="商户ID", dataType="int", required=true, paramType="path")
    @GetMapping("/{mchId}/products")
    public List<MerchantProductDTO> product(@PathVariable Integer mchId) throws APIException {
        return merchantService.selectMchProductById(mchId);
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
        if (apiConfig == null) throw new Exception("商户不存在");
        String publicKey = RSAUtil.resolvePublicKey(content);
        LambdaUpdateWrapper<ApiConfig> updateWrapper = new UpdateWrapper<ApiConfig>().lambda();
        updateWrapper.set(ApiConfig::getMchPubKey,publicKey)
                .eq(ApiConfig::getMchId,merchantId);
        apiConfigService.update(updateWrapper);
    }




}
