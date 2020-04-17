package com.esiran.greenpay.merchant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.esiran.greenpay.common.entity.APIException;
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
    MerchantDetailDTO findMerchantById(Integer id);
    void addMerchant(MerchantInputDTO merchantInputDTO) throws Exception;
    List<MerchantProductDTO> selectMchProductById(Integer mchId) throws APIException;
    MerchantProductDTO selectMchProductByIdAndPayTypeCode(Integer mchId,String payTypeCode) throws Exception;
    IPage<MerchantDTO> selectMerchantByPage(IPage<Void> page);
}
