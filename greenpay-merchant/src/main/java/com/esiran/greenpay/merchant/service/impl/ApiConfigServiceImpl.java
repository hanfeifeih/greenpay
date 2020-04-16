package com.esiran.greenpay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.merchant.entity.ApiConfig;
import com.esiran.greenpay.merchant.entity.ApiConfigDTO;
import com.esiran.greenpay.merchant.mapper.ApiConfigMapper;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户密钥 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-15
 */
@Service
public class ApiConfigServiceImpl extends ServiceImpl<ApiConfigMapper, ApiConfig> implements IApiConfigService {
    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public ApiConfigDTO findByMerchantId(Integer mchId) {
        LambdaQueryWrapper<ApiConfig> apiConfigLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiConfigLambdaQueryWrapper.eq(ApiConfig::getMchId,mchId);
        ApiConfig apiConfig = this.getOne(apiConfigLambdaQueryWrapper);
        return modelMapper.map(apiConfig,ApiConfigDTO.class);
    }
}
