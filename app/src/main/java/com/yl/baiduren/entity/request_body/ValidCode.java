package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2018/1/3.
 */

public class ValidCode extends BaseRequest {

    private String mobile;
    private int validCodeType;//1注册 ，2 修改手机号 3，登陆 4 忘记密码 5.注册子账号

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getValidCodeType() {
        return validCodeType;
    }

    public void setValidCodeType(int validCodeType) {
        this.validCodeType = validCodeType;
    }
}
