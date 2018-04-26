package com.yl.baiduren.entity.result;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/12/26.
 */

public class Assert_Mortgae_Details_Relut implements Serializable{

    /**
     * id : 1
     * name : 测试
     * amount : 333310
     * amountStr : 3333.1
     * num : 36
     * debtId : 20
     * debtRelationId : 18
     * image :
     * createTime : 1513759207000
     * updateTime : 1513759207000
     * isDelete : false
     * userId : 28
     * evaluation : 个人
     */

    private int id;
    private String name;
    private Long amount;
    private String amountStr;
    private int num;
    private int debtId;
    private int debtRelationId;
    private String image;
    private long createTime;
    private long updateTime;
    private boolean isDelete;
    private int userId;
    private String evaluation;

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDebtId() {
        return debtId;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    public int getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(int debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
