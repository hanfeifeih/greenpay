package com.esiran.greenpay.actuator;

import com.esiran.greenpay.actuator.entity.Flow;
import com.esiran.greenpay.actuator.entity.Task;

public class Main {
    private static class WxjsapiPlugin implements Plugin {

        @Override
        public void apply(Flow flow) {
            flow.add(new Task() {
                @Override
                public void action() {

                }
            });
        }
    }
    public static void main(String[] args) {

    }
}
