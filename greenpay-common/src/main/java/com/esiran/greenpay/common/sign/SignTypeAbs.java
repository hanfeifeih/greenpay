package com.esiran.greenpay.common.sign;

public abstract class SignTypeAbs implements SignType {
    private String principal;
    public SignTypeAbs(String principal){
        this.principal = principal;
    }

    public String getPrincipal() {
        return principal;
    }
}
