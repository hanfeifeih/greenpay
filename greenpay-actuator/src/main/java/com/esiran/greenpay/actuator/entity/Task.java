package com.esiran.greenpay.actuator.entity;

public abstract class Task<T> {
    public abstract String taskName();
    public abstract String dependent();
    public abstract void action(Flow<T> flow);
}
