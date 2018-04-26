package com.yl.baiduren.entity.result;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/12/26.
 */

public class Sponsor_Details_Relult implements Serializable{

    /**
     * id : 1
     * name : 段测试
     * area : 130103
     * address : 测试
     * idCode : 130622199211305017
     * phoneNumber : 17600223674
     * image :
     * debtId : 20
     * debtRelationId : 18
     * createTime : 1513760986000
     * updateTime : 1513760986000
     * isDelete : false
     * userId : 28
     * areaName : 河北省石家庄市桥东区
     */

    private int id;
    private String name;
    private String area;
    private String address;
    private String idCode;
    private String phoneNumber;
    private String image;
    private int debtId;
    private int debtRelationId;
    private long createTime;
    private long updateTime;
    private boolean isDelete;
    private int userId;
    private String areaName;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
