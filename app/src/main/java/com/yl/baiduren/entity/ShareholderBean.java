package com.yl.baiduren.entity;

/**
 * 股东信息  bean
 */

public class ShareholderBean {


    //股东姓名
    String shareholderName;
    //投资金额
    String investmentNumber;
    //股份占比
    String shareRatio;

    public String getShareholderName() {
        return shareholderName;
    }

    public void setShareholderName(String shareholderName) {
        this.shareholderName = shareholderName;
    }

    public String getInvestmentNumber() {
        return investmentNumber;
    }

    public void setInvestmentNumber(String investmentNumber) {
        this.investmentNumber = investmentNumber;
    }

    public String getShareRatio() {
        return shareRatio;
    }

    public void setShareRatio(String shareRatio) {
        this.shareRatio = shareRatio;
    }
}
