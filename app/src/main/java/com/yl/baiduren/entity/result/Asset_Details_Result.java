package com.yl.baiduren.entity.result;


import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/12/26.
 */

public class Asset_Details_Result implements Serializable{


    /**
     * id : 1
     * categoryId : 1
     * categoryName : 房产
     * name : 不知道
     * price : null
     * priceStr : null
     * num : 10
     * total : null
     * totalStr : null
     * area : 110000
     * areaName : null
     * remark : null
     * debtId : 1
     * createTime : null
     * updateTime : null
     * isDelete : null
     * userId : null
     * debtRelationId : 2
     * evaluation : null
     * imgUrl : null
     */

    private int id;
    private int categoryId;
    private String categoryName;
    private String name;
    private Object price;
    private Object priceStr;
    private int num;
    private Object total;
    private Object totalStr;
    private String area;
    private Object areaName;
    private Object remark;
    private int debtId;
    private Object createTime;
    private Object updateTime;
    private Object isDelete;
    private Object userId;
    private int debtRelationId;
    private Object evaluation;
    private String imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(Object priceStr) {
        this.priceStr = priceStr;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public Object getTotalStr() {
        return totalStr;
    }

    public void setTotalStr(Object totalStr) {
        this.totalStr = totalStr;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Object getAreaName() {
        return areaName;
    }

    public void setAreaName(Object areaName) {
        this.areaName = areaName;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public int getDebtId() {
        return debtId;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Object isDelete) {
        this.isDelete = isDelete;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public int getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(int debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public Object getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Object evaluation) {
        this.evaluation = evaluation;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
