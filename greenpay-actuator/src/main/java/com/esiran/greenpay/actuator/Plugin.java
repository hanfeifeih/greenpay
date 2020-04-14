package com.esiran.greenpay.actuator;

import com.esiran.greenpay.actuator.entity.Flow;

public interface Plugin {
    void apply(Flow flow);
}
