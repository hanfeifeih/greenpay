package com.esiran.greenpay.common.sign;

import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.common.util.RSAUtil;

public class RSASignType extends SignTypeAbs {
    public RSASignType(String principal) {
        super(principal);
    }

    @Override
    public SignVerify sign(String credential) {
        return new RSASignVerify(getPrincipal(),credential);
    }
}
