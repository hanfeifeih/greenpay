package com.esiran.greenpay.merchant.service.impl;

import com.esiran.greenpay.merchant.entity.PayAccount;
import com.esiran.greenpay.merchant.mapper.PayAccountMapper;
import com.esiran.greenpay.merchant.service.IPayAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
