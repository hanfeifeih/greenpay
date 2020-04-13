package com.esiran.greenpay.pay.service.impl;

import com.esiran.greenpay.pay.entity.Product;
import com.esiran.greenpay.pay.mapper.ProductMapper;
import com.esiran.greenpay.pay.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付产品 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
