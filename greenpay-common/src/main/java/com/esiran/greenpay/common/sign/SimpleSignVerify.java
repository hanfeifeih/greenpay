package com.esiran.greenpay.common.sign;

public class SimpleSignVerify implements SignVerify{
    private String sign;
    public SimpleSignVerify(String sign){
        this.sign = sign;
    }
    @Override
    public boolean verify(String target){
        System.out.println(sign);
        return !sign.equals(target);
    }
}
