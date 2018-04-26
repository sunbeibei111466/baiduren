package com.yl.baiduren.entity.result;

/**
 * Created by Administrator on 2018/3/29.
 */

public class CertifiedResult {

/**
 *是否个人认证（0：否；1：是）
 */
 private Boolean isPersonAuth;
 /**
 *是否企业认证（0：否；1：是）
 */
 private Boolean isCompanyAuth;

    public Boolean getPersonAuth() {
        return isPersonAuth;
    }

    public void setPersonAuth(Boolean personAuth) {
        isPersonAuth = personAuth;
    }

    public Boolean getCompanyAuth() {
        return isCompanyAuth;
    }

    public void setCompanyAuth(Boolean companyAuth) {
        isCompanyAuth = companyAuth;
    }
}
