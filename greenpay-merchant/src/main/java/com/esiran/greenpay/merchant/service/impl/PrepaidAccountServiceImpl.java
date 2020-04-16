package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.merchant.entity.PayAccount;
import com.esiran.greenpay.merchant.entity.PayAccountDTO;
import com.esiran.greenpay.merchant.entity.PrepaidAccount;
import com.esiran.greenpay.merchant.entity.PrepaidAccountDTO;
import com.esiran.greenpay.merchant.mapper.PrepaidAccountMapper;
import com.esiran.greenpay.merchant.service.IPrepaidAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户预充值账户 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class PrepaidAccountServiceImpl extends ServiceImpl<PrepaidAccountMapper, PrepaidAccount> implements IPrepaidAccountService {
    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public PrepaidAccountDTO findByMerchantId(Integer mchId) {
        LambdaQueryWrapper<PrepaidAccount> payAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
        payAccountLambdaQueryWrapper.eq(PrepaidAccount::getMerchantId,mchId);
        PrepaidAccount prepaidAccount = this.getOne(payAccountLambdaQueryWrapper);
        return modelMapper.map(prepaidAccount,PrepaidAccountDTO.class);
    }
}
