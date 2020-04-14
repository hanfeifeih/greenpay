package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantProduct;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import com.esiran.greenpay.merchant.mapper.MerchantMapper;
import com.esiran.greenpay.merchant.service.IMerchantProductService;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.pay.entity.Product;
import com.esiran.greenpay.pay.entity.Type;
import com.esiran.greenpay.pay.service.IProductService;
import com.esiran.greenpay.pay.service.ITypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商户 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {
    private final ITypeService iTypeService;
    private final IProductService productService;
    private final IMerchantProductService merchantProductService;
    public MerchantServiceImpl(ITypeService iTypeService, IProductService productService, IMerchantProductService merchantProductService) {
        this.iTypeService = iTypeService;
        this.productService = productService;
        this.merchantProductService = merchantProductService;
    }

    @Override
    public List<MerchantProductDTO> selectMchProductById(Integer mchId) {
        List<Type> types = iTypeService.list();
        List<MerchantProductDTO> merchantProductDTOS = new ArrayList<>();
        for (Type type : types){
            MerchantProductDTO dto = new MerchantProductDTO();
            dto.setMerchantId(mchId);
            dto.setPayTypeName(type.getTypeName());
            dto.setPayTypeCode(type.getTypeCode());
            dto.setStatus(false);
            LambdaQueryWrapper<MerchantProduct> merchantProductQueryWrapper =
                    new QueryWrapper<MerchantProduct>().lambda().eq(MerchantProduct::getMerchantId,mchId)
                            .eq(MerchantProduct::getPayTypeCode,type.getTypeCode());
            MerchantProduct merchantProduct = merchantProductService.getOne(merchantProductQueryWrapper);
            if (merchantProduct == null){
                merchantProductDTOS.add(dto);
                continue;
            }
            Product product = productService.getById(merchantProduct.getProductId());
            if (product == null) {
                merchantProductDTOS.add(dto);
                continue;
            }
            dto.setProductId(product.getId());
            dto.setProductName(product.getProductName());
            dto.setStatus(merchantProduct.getStatus());
            dto.setRate(merchantProduct.getRate());
            merchantProductDTOS.add(dto);
        }
        return merchantProductDTOS;
    }

    @Override
    public MerchantProductDTO selectMchProductByIdAndPayTypeCode(Integer mchId, String payTypeCode) throws Exception {
        LambdaQueryWrapper<Type> typeQueryWrapper = new QueryWrapper<Type>()
                .lambda().eq(Type::getTypeCode,payTypeCode);
        Type type = iTypeService.getOne(typeQueryWrapper);
        if (type == null){
            throw new Exception("通道编码不存在");
        }
        LambdaQueryWrapper<MerchantProduct> merchantProductQueryWrapper =
                new QueryWrapper<MerchantProduct>().lambda().eq(MerchantProduct::getMerchantId,mchId)
                        .eq(MerchantProduct::getPayTypeCode,type.getTypeCode());
        MerchantProduct merchantProduct = merchantProductService.getOne(merchantProductQueryWrapper);
        MerchantProductDTO dto = new MerchantProductDTO();
        dto.setMerchantId(mchId);
        dto.setPayTypeName(type.getTypeName());
        dto.setPayTypeCode(type.getTypeCode());
        dto.setStatus(false);
        if (merchantProduct == null){
            return dto;
        }
        Product product = productService.getById(merchantProduct.getProductId());
        if (product == null) {
            return dto;
        }
        dto.setProductId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setStatus(merchantProduct.getStatus());
        dto.setRate(merchantProduct.getRate());
        return dto;
    }
}
