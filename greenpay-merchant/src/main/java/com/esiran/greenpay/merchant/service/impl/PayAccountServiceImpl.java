package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.merchant.entity.ApiConfig;
import com.esiran.greenpay.merchant.entity.ApiConfigDTO;
import com.esiran.greenpay.merchant.entity.PayAccount;
import com.esiran.greenpay.merchant.entity.PayAccountDTO;
import com.esiran.greenpay.merchant.mapper.PayAccountMapper;
import com.esiran.greenpay.merchant.service.IPayAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户支付账户 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class PayAccountServiceImpl extends ServiceImpl<PayAccountMapper, PayAccount> implements IPayAccountService {
    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public PayAccountDTO findByMerchantId(Integer mchId) {
        LambdaQueryWrapper<PayAccount> payAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
        payAccountLambdaQueryWrapper.eq(PayAccount::getMerchantId,mchId);
        PayAccount payAccount = this.getOne(payAccountLambdaQueryWrapper);
        return modelMapper.map(payAccount,PayAccountDTO.class);
    }
}
