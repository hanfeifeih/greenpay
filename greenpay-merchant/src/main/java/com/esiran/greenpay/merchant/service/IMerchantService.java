package com.esiran.greenpay.merchant.service;

import com.esiran.greenpay.merchant.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

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
    MerchantDTO findMerchantById(Integer id);
    void addMerchant(MerchantInputDTO merchantInputDTO) throws Exception;
    List<MerchantProductDTO> selectMchProductById(Integer mchId);
    MerchantProductDTO selectMchProductByIdAndPayTypeCode(Integer mchId,String payTypeCode) throws Exception;
}
