package com.esiran.greenpay.openapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.esiran.greenpay.openapi.entity.Invoice;
import com.esiran.greenpay.openapi.entity.InvoiceInputDTO;
import com.esiran.greenpay.openapi.service.IInvoiceService;
import com.esiran.greenpay.pay.entity.Interface;
import com.esiran.greenpay.pay.entity.Passage;
import com.esiran.greenpay.pay.entity.PassageAccount;
import com.esiran.greenpay.pay.entity.Product;
import com.esiran.greenpay.pay.service.IInterfaceService;
import com.esiran.greenpay.pay.service.IPassageAccountService;
import com.esiran.greenpay.pay.service.IPassageService;
import com.esiran.greenpay.pay.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvoiceService implements IInvoiceService {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final IMerchantService merchantService;
    private final IProductService productService;
    private final IPassageAccountService passageAccountService;
    private final IPassageService passageService;
    private final IInterfaceService interfaceService;
    public InvoiceService(
            IMerchantService merchantService,
            IProductService productService,
            IPassageAccountService passageAccountService,
            IPassageService passageService,
            IInterfaceService interfaceService) {
        this.merchantService = merchantService;
        this.productService = productService;
        this.passageAccountService = passageAccountService;
        this.passageService = passageService;
        this.interfaceService = interfaceService;
    }

    @Override
    public Invoice createInvoiceByInput(InvoiceInputDTO invoiceInputDTO, Merchant merchant) throws Exception {
        Invoice invoice = modelMapper.map(invoiceInputDTO,Invoice.class);
        invoice.setCreatedAt(LocalDateTime.now());
        if (!merchant.getStatus()){
            throw new APIException("商户状态已锁定","MERCHANT_STATUS_LOCKED");
        }
        MerchantProductDTO merchantProductDTO =
                merchantService.selectMchProductByIdAndPayTypeCode(merchant.getId(),invoice.getChannel());
        if (!merchantProductDTO.getStatus()){
            throw new APIException("商户支付渠道未开通","PAY_TYPE_LOCKED");
        }
        Product product = productService.getById(merchantProductDTO.getProductId());
        if (product == null){
            throw new APIException("系统错误，获取支付产品失败","PAY_PRODUCT_NOT_FOUND");
        }
        if (!product.getStatus()){
            throw new APIException("商户支付渠道未开通","PAY_PRODUCT_LOCKED");
        }
        Passage passage = null;
        PassageAccount passageAccount = null;
        if (product.getInterfaceMode() == 1){
            passage = passageService.getById(product.getDefaultPassageId());
            passageAccount = passageAccountService.getById(product.getDefaultPassageAccId());
        }
        if (passage == null){
            throw new APIException("系统错误，获取支付通道失败","PAY_PASSAGE_NOT_FOUND");
        }
        if (!passage.getStatus()){
            throw new APIException("商户支付渠道未开通","PAY_PASSAGE_LOCKED");
        }
        if (passageAccount == null){
            throw new APIException("系统错误，获取通道账户失败","PAY_PASSAGE_ACCOUNT_NOT_FOUND");
        }
        if (!passageAccount.getStatus()){
            throw new APIException("商户支付渠道未开通","PAY_PASSAGE_ACCOUNT_LOCKED");
        }
        LambdaQueryWrapper<Interface> lambdaQueryWrapper =
                new QueryWrapper<Interface>().lambda()
                        .eq(Interface::getInterfaceCode,passage.getInterfaceCode());
        Interface ints = interfaceService.getOne(lambdaQueryWrapper);
        if (ints == null){
            throw new APIException("系统错误，获取支付接口失败","PAY_INTERFACE_NOT_FOUND");
        }
        if (!ints.getStatus()){
            throw new APIException("商户支付渠道未开通","PAY_INTERFACE_LOCKED");
        }
        return invoice;
    }
}
