package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/4/20.
 */

public class Yan_Zheng_Code_Body extends BaseRequest {
    private String validCode;//验证码
    private int validCodeType;

    public Yan_Zheng_Code_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public int getValidCodeType() {
        return validCodeType;
    }

    public void setValidCodeType(int validCodeType) {
        this.validCodeType = validCodeType;
    }
}
