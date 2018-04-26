package com.yl.baiduren.entity.result;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Administrator on 2018/3/29.
 */

public class UserResponse extends BaseRequest {

    /**
     * 申请人姓名
     */
    private String personName;
    /**
     * 申请人身份证号
     */
    private String idCode;
    /**
     * 申请人手机号
     */
    private String mobile;
    /**
     * 申请人代表机构
     */
    private String companyName;

    public UserResponse(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
