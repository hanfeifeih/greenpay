package com.esiran.greenpay.merchant.service.impl;

import com.esiran.greenpay.merchant.entity.ApiConfig;
import com.esiran.greenpay.merchant.mapper.ApiConfigMapper;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
