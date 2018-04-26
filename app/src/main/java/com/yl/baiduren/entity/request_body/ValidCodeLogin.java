package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2018/1/4.
 */

public class ValidCodeLogin extends BaseRequest {

    private String mobile;
    private String validCode;
    private int validCodeType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
