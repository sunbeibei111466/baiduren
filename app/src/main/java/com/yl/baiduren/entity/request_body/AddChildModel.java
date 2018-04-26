package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2017/12/25.
 */

public class AddChildModel extends BaseRequest{

    private String userName;//	用户名
    private String password;//	密码
    private String mobile;//	手机号
    private String realName;//真实姓名
    private String idCard;//身份证号
    private String area;//所属地区
    private String address	;//详细地址
    private String validCode;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
