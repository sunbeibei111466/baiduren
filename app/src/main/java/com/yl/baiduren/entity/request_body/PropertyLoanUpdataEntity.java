package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2017/12/27.
 */

public class PropertyLoanUpdataEntity extends BaseRequest {


    private int id;
    private int debtRelationId;
    private String name;//标的名称
    private String unit;//计量单位
    private String owner;//产权归属 code
    private int rightYear;//产权年限
    private String rightValueStr;//评估价值
    private String rightEvaluation;//评估机构
    //专利
    private String patentNo;//专利号
    private int patentYear;//专利年限
    private String patentValueStr;//专利评估价值
    private String patentEvaluation;//专利评估机构

    private Long returnMoney;//已还合计
    private Long returnMoneyStr;//已还合计
    private String balanceStr;//未还余额
    private String image;//图片

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getRightYear() {
        return rightYear;
    }

    public void setRightYear(int rightYear) {
        this.rightYear = rightYear;
    }

    public String getRightValueStr() {
        return rightValueStr;
    }

    public void setRightValueStr(String rightValueStr) {
        this.rightValueStr = rightValueStr;
    }

    public String getRightEvaluation() {
        return rightEvaluation;
    }

    public void setRightEvaluation(String rightEvaluation) {
        this.rightEvaluation = rightEvaluation;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public int getPatentYear() {
        return patentYear;
    }

    public void setPatentYear(int patentYear) {
        this.patentYear = patentYear;
    }

    public String getPatentValueStr() {
        return patentValueStr;
    }

    public void setPatentValueStr(String patentValueStr) {
        this.patentValueStr = patentValueStr;
    }

    public String getPatentEvaluation() {
        return patentEvaluation;
    }

    public void setPatentEvaluation(String patentEvaluation) {
        this.patentEvaluation = patentEvaluation;
    }

    public Long getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Long returnMoney) {
        this.returnMoney = returnMoney;
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

    public Long getReturnMoneyStr() {
        return returnMoneyStr;
    }

    public void setReturnMoneyStr(Long returnMoneyStr) {
        this.returnMoneyStr = returnMoneyStr;
    }
}
