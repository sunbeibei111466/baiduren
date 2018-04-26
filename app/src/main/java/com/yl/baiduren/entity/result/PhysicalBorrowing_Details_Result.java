package com.yl.baiduren.entity.result;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/12/26.
 */

public class PhysicalBorrowing_Details_Result implements Serializable{

    /**
     * id : 1
     * name : 123
     * unit : 1232
     * num : 123
     * value : 2300
     * valueStr : 23.0
     * evaluation : 123
     * depreciation : 0.12
     * borrowTime : 1511107200000
     * useLife : 123
     * returnNum : 123
     * balance : 12300
     * balanceStr : 123.0
     * image :
     * isDelete : null
     * debtRelationId : 18
     * userId : 28
     * createTime : 1513766210000
     * updateTime : 1513766210000
     */

    private int id;
    private String name;
    private String unit;
    private int num;
    private int value;
    private String valueStr;
    private String evaluation;
    private double depreciation;
    private long borrowTime;
    private int useLife;
    private int returnNum;
    private int balance;
    private String balanceStr;
    private String image;
    private Object isDelete;
    private int debtRelationId;
    private int userId;
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public long getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(long borrowTime) {
        this.borrowTime = borrowTime;
    }

    public int getUseLife() {
        return useLife;
    }

    public void setUseLife(int useLife) {
        this.useLife = useLife;
    }

    public int getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(int returnNum) {
        this.returnNum = returnNum;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
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

    public Object getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Object isDelete) {
        this.isDelete = isDelete;
    }

    public int getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(int debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
