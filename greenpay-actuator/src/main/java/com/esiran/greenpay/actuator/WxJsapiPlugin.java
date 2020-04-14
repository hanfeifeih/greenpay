package com.esiran.greenpay.actuator;

import com.esiran.greenpay.actuator.entity.Flow;
import com.esiran.greenpay.actuator.entity.Task;

public class WxJsapiPlugin implements Plugin<Object>{
    @Override
    public void apply(Flow<Object> flow) {
        flow.add(new CreatePayOrderTask());
    }

    public static class CreatePayOrderTask extends Task<Object> {

        @Override
        public String taskName() {
            return "create_pay_order";
        }

        @Override
        public String dependent() {
            return "create_order";
        }

        @Override
        public void action(Flow<Object> flow) {
            flow.getData();
        }
    }
}
