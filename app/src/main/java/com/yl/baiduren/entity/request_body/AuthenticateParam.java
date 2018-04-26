package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/3/29.
 */

public class AuthenticateParam extends BaseRequest{
    /**
     * 认证身份 1.个人；2.企业
     */
    private Integer type;

    /**
     * 个人姓名
     */
    private String personName;
    /**
     * 个人身份证号
     */
    private String personIdCode;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 企业工商注册名称
     */
    private String companyName;
    /**
     * 验证码
     */
    private String valiCode;
    /**
     * 验证码类型（为6）
     */
    private Integer validCodeType;
    /**
     * 组织机构代码
     */
    private String companyCode;
    /**
     * 企业所在地详细地址
     */
    private String address;
    /**
     * 法人代表姓名
     */
    private String legalName;
    /**
     * 法人身份证号
     */
    private String legalIdCode;
    /**
     * 证明图片
     */
    private String image;
    /**
     * 所属地
     */
    private String personArea;
    /**
     * 详细地址
     */
    private String personAddress;

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getPersonArea() {
        return personArea;
    }

    public void setPersonArea(String personArea) {
        this.personArea = personArea;
    }

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public Integer getValidCodeType() {
        return validCodeType;
    }

    public void setValidCodeType(Integer validCodeType) {
        this.validCodeType = validCodeType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonIdCode() {
        return personIdCode;
    }

    public void setPersonIdCode(String personIdCode) {
        this.personIdCode = personIdCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalIdCode() {
        return legalIdCode;
    }

    public void setLegalIdCode(String legalIdCode) {
        this.legalIdCode = legalIdCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AuthenticateParam(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
