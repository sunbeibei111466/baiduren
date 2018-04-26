package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Administrator on 2018/3/29.
 */

public class AuthorizeParam extends BaseRequest {


    /**
     * 接收方
     */
    private String searchName;
    /**
     * 发起方
     */
    private Long acceptId;
    /**
     * 申请原因
     */
    private String sendReason;
    /** 验证码 */
    private String validCode;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码类型
     */
    private Integer validCodeType;

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setValidCodeType(Integer validCodeType) {
        this.validCodeType = validCodeType;
    }



    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Long getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Long acceptId) {
        this.acceptId = acceptId;
    }

    public String getSendReason() {
        return sendReason;
    }

    public void setSendReason(String sendReason) {
        this.sendReason = sendReason;
    }

    public AuthorizeParam(BaseRequest baseRequest) {
        super(baseRequest);
    }


}
