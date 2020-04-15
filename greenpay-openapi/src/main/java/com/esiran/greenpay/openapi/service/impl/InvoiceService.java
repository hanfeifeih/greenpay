package com.esiran.greenpay.openapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.esiran.greenpay.actuator.Plugin;
import com.esiran.greenpay.actuator.PluginLoader;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.common.util.IdWorker;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.esiran.greenpay.openapi.entity.Invoice;
import com.esiran.greenpay.openapi.entity.InvoiceInputDTO;
import com.esiran.greenpay.openapi.entity.PayOrder;
import com.esiran.greenpay.openapi.plugins.PayOrderFlow;
import com.esiran.greenpay.openapi.plugins.WxJsapiPlugin;
import com.esiran.greenpay.openapi.service.IInvoiceService;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InvoiceService implements IInvoiceService {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final IMerchantService merchantService;
    private final IProductService productService;
    private final IPassageAccountService passageAccountService;
    private final IPassageService passageService;
    private final IInterfaceService interfaceService;
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;
    private final IdWorker idWorker;
    public InvoiceService(
            IMerchantService merchantService,
            IProductService productService,
            IPassageAccountService passageAccountService,
            IPassageService passageService,
            IInterfaceService interfaceService, IOrderService orderService,
            IOrderDetailService orderDetailService, IdWorker idWorker) {
        this.merchantService = merchantService;
        this.productService = productService;
        this.passageAccountService = passageAccountService;
        this.passageService = passageService;
        this.interfaceService = interfaceService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.idWorker = idWorker;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Invoice createInvoiceByInput(InvoiceInputDTO invoiceInputDTO, Merchant merchant) throws Exception {
        Invoice invoice = modelMapper.map(invoiceInputDTO,Invoice.class);
        invoice.setOrderNo(String.valueOf(idWorker.nextId()));
        invoice.setOrderSn(EncryptUtil.baseTimelineCode());
        invoice.setCreatedAt(LocalDateTime.now());
        if (!merchant.getStatus()){
            throw new APIException("商户状态已锁定","MERCHANT_STATUS_LOCKED");
        }
        MerchantProductDTO merchantProductDTO;
        try {
            merchantProductDTO = merchantService
                    .selectMchProductByIdAndPayTypeCode(merchant.getId(),invoice.getChannel());
        }catch (Exception e){
            throw new APIException("商户支付渠道未开通","PAY_TYPE_NOT_FOUND");
        }
        if (!merchantProductDTO.getStatus() ){
            throw new APIException("商户支付渠道未开通","PAY_TYPE_LOCKED");
        }
        Product product = productService.getById(merchantProductDTO.getProductId());
        if (product == null || !product.getStatus()){
            throw new APIException("系统错误，获取支付产品失败","PAY_PRODUCT_NOT_FOUND");
        }
        Passage passage = null;
        PassageAccount passageAccount = null;
        if (product.getInterfaceMode() == 1){
            passage = passageService.getById(product.getDefaultPassageId());
            passageAccount = passageAccountService.getById(product.getDefaultPassageAccId());
        }
        if (passage == null ||!passage.getStatus()){
            throw new APIException("系统错误，获取支付通道失败","PAY_PASSAGE_NOT_FOUND");
        }
        if (passageAccount == null || !passageAccount.getStatus()){
            throw new APIException("系统错误，获取通道账户失败","PAY_PASSAGE_ACCOUNT_NOT_FOUND");
        }
        LambdaQueryWrapper<Interface> lambdaQueryWrapper =
                new QueryWrapper<Interface>().lambda()
                        .eq(Interface::getInterfaceCode,passage.getInterfaceCode());
        Interface ints = interfaceService.getOne(lambdaQueryWrapper);
        if (ints == null || !ints.getStatus()){
            throw new APIException("系统错误，获取支付接口失败","PAY_INTERFACE_NOT_FOUND");
        }
        // 构造订单
        Order order = modelMapper.map(invoice,Order.class);
        order.setStatus(0);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setMchId(merchant.getId());
        LambdaQueryWrapper<Order> orderQueryWrapper = new QueryWrapper<Order>().lambda()
                .eq(Order::getMchId,order.getMchId())
                .eq(Order::getAppId,order.getAppId())
                .eq(Order::getOutOrderNo,order.getOutOrderNo());
        Order oldOrder = orderService.getOne(orderQueryWrapper);
        if (oldOrder != null){
            throw new APIException("商户订单号不能重复","ORDER_NO_DUPLICATE");
        }
        orderService.save(order);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderNo(invoice.getOrderNo());
        orderDetail.setPayTypeCode(invoice.getChannel());
        orderDetail.setPayProductId(product.getId());
        orderDetail.setPayPassageId(passage.getId());
        orderDetail.setPayPassageAccId(passageAccount.getId());
        orderDetail.setPayInterfaceId(ints.getId());
        orderDetail.setPayInterfaceParams(passageAccount.getInterfaceAttr());
        orderDetail.setCreatedAt(LocalDateTime.now());
        orderDetail.setUpdatedAt(LocalDateTime.now());
        orderDetailService.save(orderDetail);
        Plugin<PayOrder> plugin = PluginLoader.loadForClassPath("com.esiran.greenpay.openapi.plugins.WxJsapiPlugin");
        PayOrder payOrder = new PayOrder();
        PayOrderFlow payOrderFlow = new PayOrderFlow(payOrder);
        plugin.apply(payOrderFlow);
        payOrderFlow.execDependent("create");
        return invoice;
    }
}
