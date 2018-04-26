package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.entity.greenentity.GoodLoanDO;
import com.yl.baiduren.entity.greenentity.MortgageDO;
import com.yl.baiduren.entity.greenentity.PropertyLoanDO;
import com.yl.baiduren.entity.greenentity.SponsorDO;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/20.
 */

public class DebtRelationVo4 {

    private Long debtRelationId;
    private List<SponsorDO> sponsors;//担保人
    private List<MortgageDO> mortgages;//资产抵押
    private List<MoneyLoan> moneyLoans;//货币借贷
    private List<GoodLoanDO> goodLoans;//实物借贷
    private List<PropertyLoanDO> propertyLoans;//产权借贷

    public Long getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(Long debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public List<SponsorDO> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<SponsorDO> sponsors) {
        this.sponsors = sponsors;
    }

    public List<MortgageDO> getMortgages() {
        return mortgages;
    }

    public void setMortgages(List<MortgageDO> mortgages) {
        this.mortgages = mortgages;
    }

    public List<MoneyLoan> getMoneyLoans() {
        return moneyLoans;
    }

    public void setMoneyLoans(List<MoneyLoan> moneyLoans) {
        this.moneyLoans = moneyLoans;
    }

    public List<GoodLoanDO> getGoodLoans() {
        return goodLoans;
    }

    public void setGoodLoans(List<GoodLoanDO> goodLoans) {
        this.goodLoans = goodLoans;
    }

    public List<PropertyLoanDO> getPropertyLoans() {
        return propertyLoans;
    }

    public void setPropertyLoans(List<PropertyLoanDO> propertyLoans) {
        this.propertyLoans = propertyLoans;
    }
}
