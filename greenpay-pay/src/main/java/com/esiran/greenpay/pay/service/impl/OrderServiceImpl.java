package com.esiran.greenpay.pay.service.impl;

import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.mapper.OrderMapper;
import com.esiran.greenpay.pay.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付订单 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
