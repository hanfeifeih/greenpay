package com.esiran.greenpay.merchant.service;

import com.esiran.greenpay.merchant.entity.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.merchant.entity.MerchantProduct;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;

import java.util.List;

/**
 * <p>
 * 商户 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
public interface IMerchantService extends IService<Merchant> {
    List<MerchantProductDTO> selectMchProductById(Integer mchId);
    MerchantProductDTO selectMchProductByIdAndPayTypeCode(Integer mchId,String payTypeCode) throws Exception;
}
