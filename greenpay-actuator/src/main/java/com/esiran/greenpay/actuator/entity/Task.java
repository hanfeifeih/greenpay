package com.esiran.greenpay.actuator.entity;

public abstract class Task {
    protected String flowName;
    protected String dependent;

    public abstract void action();
}
