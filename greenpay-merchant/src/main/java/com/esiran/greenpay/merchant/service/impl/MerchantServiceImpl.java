package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.common.util.RSAUtil;
import com.esiran.greenpay.merchant.entity.*;
import com.esiran.greenpay.merchant.mapper.MerchantMapper;
import com.esiran.greenpay.merchant.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.pay.entity.Product;
import com.esiran.greenpay.pay.entity.Type;
import com.esiran.greenpay.pay.service.IProductService;
import com.esiran.greenpay.pay.service.ITypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
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
    private final IApiConfigService apiConfigService;
    private final IPayAccountService payAccountService;
    private final IPrepaidAccountService prepaidAccountService;
    private static final ModelMapper modelMapper = new ModelMapper();
    public MerchantServiceImpl(
            ITypeService iTypeService,
            IProductService productService,
            IMerchantProductService merchantProductService,
            IApiConfigService apiConfigService,
            IPayAccountService payAccountService,
            IPrepaidAccountService prepaidAccountService) {
        this.iTypeService = iTypeService;
        this.productService = productService;
        this.merchantProductService = merchantProductService;
        this.apiConfigService = apiConfigService;
        this.payAccountService = payAccountService;
        this.prepaidAccountService = prepaidAccountService;
    }

    @Override
    public MerchantDetailDTO findMerchantById(Integer id) {
        Merchant merchant = getById(id);
        MerchantDetailDTO dto = modelMapper.map(merchant, MerchantDetailDTO.class);
        ApiConfigDTO apiConfigDTO = apiConfigService.findByMerchantId(merchant.getId());
        String publicKeyVal = String.format("%s\r\n%s\r\n%s",
                RSAUtil.PEM_FILE_PUBLIC_PKCS1_BEGIN,
                apiConfigDTO.getPubKey(),
                RSAUtil.PEM_FILE_PUBLIC_PKCS1_END);
        apiConfigDTO.setPubKeyVal(publicKeyVal);
        PayAccountDTO payAccountDTO = payAccountService.findByMerchantId(merchant.getId());
        PrepaidAccountDTO prepaidAccountDTO = prepaidAccountService.findByMerchantId(merchant.getId());
        dto.setApiConfig(apiConfigDTO);
        dto.setPayAccount(payAccountDTO);
        dto.setPrepaidAccount(prepaidAccountDTO);
        return dto;
    }

    @Override
    @Transactional
    public void addMerchant(MerchantInputDTO merchantInputDTO) throws Exception {
        Merchant merchant = modelMapper.map(merchantInputDTO,Merchant.class);
        LambdaQueryWrapper<Merchant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Merchant::getUsername, merchant.getUsername()).or()
                .eq(Merchant::getEmail, merchant.getEmail());
        Merchant oldMerchant = getOne(queryWrapper);
        if (oldMerchant != null){
            throw new Exception("用户名或邮箱已存在");
        }
        save(merchant);
        // api 配置信息构造
        KeyPair keyPair = RSAUtil.generateKeyPair();
        String privateKey = RSAUtil.getPrivateKey(keyPair);
        String publicKey = RSAUtil.getPublicKey(keyPair);
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setMchId(merchant.getId());
        apiConfig.setApiKey(EncryptUtil.md5(EncryptUtil.baseTimelineCode()));
        apiConfig.setApiSecurity(EncryptUtil.md5(EncryptUtil.baseTimelineCode()));
        apiConfig.setPrivateKey(privateKey);
        apiConfig.setPubKey(publicKey);
        apiConfigService.save(apiConfig);
        // 商户账户信息初始化
        PayAccount payAccount = new PayAccount();
        payAccount.setMerchantId(merchant.getId());
        payAccount.setAvailBalance(0);
        payAccount.setFreezeBalance(0);
        payAccountService.save(payAccount);
        PrepaidAccount prepaidAccount = new PrepaidAccount();
        prepaidAccount.setMerchantId(merchant.getId());
        prepaidAccount.setAvailBalance(0);
        prepaidAccount.setFreezeBalance(0);
        prepaidAccountService.save(prepaidAccount);
    }

    @Override
    public List<MerchantProductDTO> selectMchProductById(Integer mchId) throws APIException {
        Merchant merchant = getById(mchId);
        if (merchant == null){
            throw new APIException("商户号不存在","MERCHANT_NOT_FOUND");
        }
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

    @Override
    public IPage<MerchantDTO> selectMerchantByPage(IPage<Void> page) {
        IPage<Merchant> merchantPage = this.page(new Page<>(page.getCurrent(),page.getSize()));
        return merchantPage.convert(item-> modelMapper.map(item,MerchantDTO.class));
    }
}
