package com.esiran.greenpay.openapi.plugins;

import com.esiran.greenpay.actuator.entity.Flow;
import com.esiran.greenpay.openapi.entity.PayOrder;

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

}
