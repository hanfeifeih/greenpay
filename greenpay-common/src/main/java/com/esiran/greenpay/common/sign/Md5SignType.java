package com.esiran.greenpay.common.sign;

import com.esiran.greenpay.common.util.EncryptUtil;

public class Md5SignType extends SignTypeAbs{
    public Md5SignType(String principal) {
        super(principal);
    }

    @Override
    public SignVerify sign(String credential) {
        return new SimpleSignVerify(
                EncryptUtil.md5(this.getPrincipal().concat(credential))
        );
    }
}
