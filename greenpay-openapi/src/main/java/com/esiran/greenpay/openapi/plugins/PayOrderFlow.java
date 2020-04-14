package com.esiran.greenpay.openapi.plugins;

import com.esiran.greenpay.actuator.entity.Flow;
import com.esiran.greenpay.openapi.entity.PayOrder;
import me.chanjar.weixin.mp.api.WxMpService;

import java.util.HashMap;
import java.util.Map;

public class PayOrderFlow extends Flow<PayOrder> {
    public PayOrderFlow(PayOrder data) {
        super(data);
    }

    @Override
    public PayOrder getData() {
        return null;
    }

    @Override
    public void setData(PayOrder data) {

    }

    @Override
    public void update(PayOrder payOrder) {

    }

    @Override
    public Map<String, Object> loadContext() {
        Map<String, Object> map = new HashMap<>();
//        map.put(WxMpService.class.getName(),)
        return new HashMap<>();
    }
}
