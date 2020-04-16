package com.esiran.greenpay.merchant.service;

import com.esiran.greenpay.merchant.entity.PrepaidAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.merchant.entity.PrepaidAccountDTO;

/**
 * <p>
 * 商户预充值账户 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
public interface IPrepaidAccountService extends IService<PrepaidAccount> {
    PrepaidAccountDTO findByMerchantId(Integer mchId);
}
