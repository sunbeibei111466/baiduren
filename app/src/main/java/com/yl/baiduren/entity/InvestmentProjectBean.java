package com.yl.baiduren.entity;

import java.util.List;

/**
 * 投资项目   bean
 */

public class InvestmentProjectBean {

    String investmentProject;
    List<ShareholderBean> shareholderBeen;


    public String getInvestmentProject() {
        return investmentProject;
    }

    public void setInvestmentProject(String investmentProject) {
        this.investmentProject = investmentProject;
    }

    public List<ShareholderBean> getShareholderBeen() {
        return shareholderBeen;
    }

    public void setShareholderBeen(List<ShareholderBean> shareholderBeen) {
        this.shareholderBeen = shareholderBeen;
    }
}
