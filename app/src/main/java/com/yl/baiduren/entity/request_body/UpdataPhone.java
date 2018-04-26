package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2017/12/25.
 */

public class UpdataPhone extends BaseRequest {

    private String mobile;
    private String standbyMobile;
    private String validCode;//验证码
    private int validCodeType;
    private Long childUserId;//子账号iD

    public UpdataPhone(BaseRequest baseRequest) {
        super(baseRequest);
    }

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


    public String getStandbyMobile() {
        return standbyMobile;
    }

    public void setStandbyMobile(String standbyMobile) {
        this.standbyMobile = standbyMobile;
    }

    public int getValidCodeType() {
        return validCodeType;
    }

    public void setValidCodeType(int validCodeType) {
        this.validCodeType = validCodeType;
    }

    public Long getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(Long childUserId) {
        this.childUserId = childUserId;
    }
}
