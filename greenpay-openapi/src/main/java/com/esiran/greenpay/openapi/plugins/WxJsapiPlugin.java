package com.esiran.greenpay.openapi.plugins;

import com.esiran.greenpay.actuator.Plugin;
import com.esiran.greenpay.actuator.entity.Flow;
import com.esiran.greenpay.actuator.entity.Task;
import com.esiran.greenpay.openapi.entity.PayOrder;
import com.esiran.greenpay.pay.entity.OrderDetail;

public class WxJsapiPlugin implements Plugin<PayOrder> {
    private static final class CreatePayOrderTask extends Task<PayOrder> {

        @Override
        public String taskName() {
            return "create_pay_order";
        }

        @Override
        public String dependent() {
            return "create";
        }

        @Override
        public void action(Flow<PayOrder> flow) {
            PayOrder payOrder = flow.getData();
//            payOrder.getOrder();
        }
    }
    private static final class PayOrderNotifyTask extends Task<PayOrder> {
        @Override
        public String taskName() {
            return "pay_order_notify";
        }

        @Override
        public String dependent() {
            return "notify";
        }

        @Override
        public void action(Flow<PayOrder> flow) {
            PayOrder payOrder = flow.getData();
            System.out.println("执行回调订单");
        }
    }
    @Override
    public void apply(Flow<PayOrder> flow) {
        flow.add(new CreatePayOrderTask());
        flow.add(new PayOrderNotifyTask());
    }
}
