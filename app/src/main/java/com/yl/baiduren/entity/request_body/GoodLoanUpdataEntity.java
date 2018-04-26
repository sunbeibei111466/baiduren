package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2017/12/27.
 */

public class GoodLoanUpdataEntity extends BaseRequest {

    private int id;
    private int debtRelationId;
    private String name;//物品名称
    private String unit;//计量单位
    private int num;//数量
    private String valueStr;//评估价值
    private String evaluation;//评估机构
    private double depreciation;//折旧率
    private Long borrowTime;//借用日期
    private int useLife;//使用年限
    private Long returnNum;//归还数量
    private String balanceStr;//未还金额
    private String image;//图片

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(double depreciation) {
        this.depreciation = depreciation;
    }

    public Long getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Long borrowTime) {
        this.borrowTime = borrowTime;
    }

    public int getUseLife() {
        return useLife;
    }

    public void setUseLife(int useLife) {
        this.useLife = useLife;
    }

    public Long getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Long returnNum) {
        this.returnNum = returnNum;
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
}
