package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.greenentity.PayRecord;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/27.
 */

public class MoneyLoanUpdataEntity extends BaseRequest {

    private int id;
    private int debtRelationId;
    private String type;//币种
    private String principalStr;//本金
    private double interest;//利息
    private Long loanTime;//放款时间
    private Long endTime;//'截止时间
    private List<PayRecord> payRecordList;
    private String payMoneyStr;//已还合计 (元)
    private String balanceStr;//剩余金额(元)
    private String image;
    private Boolean isPay;

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(int debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrincipalStr() {
        return principalStr;
    }

    public void setPrincipalStr(String principalStr) {
        this.principalStr = principalStr;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public Long getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Long loanTime) {
        this.loanTime = loanTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<PayRecord> getPayRecordList() {
        return payRecordList;
    }

    public void setPayRecordList(List<PayRecord> payRecordList) {
        this.payRecordList = payRecordList;
    }

    public String getPayMoneyStr() {
        return payMoneyStr;
    }

    public void setPayMoneyStr(String payMoneyStr) {
        this.payMoneyStr = payMoneyStr;
    }

    public String getBalanceStr() {
        return balanceStr;
    }

    public void setBalanceStr(String balanceStr) {
        this.balanceStr = balanceStr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
