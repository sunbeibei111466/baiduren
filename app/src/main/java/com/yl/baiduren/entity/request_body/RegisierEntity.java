package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2017/12/11.
 */

public class RegisierEntity extends BaseRequest{


    private String userName;//	用户名
    private String password;//	密码
    private String realName;//真实姓名
    private String mobile;//手机号
    private String validCode;//验证码
    private String sex;//男女
    private int validCodeType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getValidCodeType() {
        return validCodeType;
    }

    public void setValidCodeType(int validCodeType) {
        this.validCodeType = validCodeType;
    }
}
