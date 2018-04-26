package com.yl.baiduren.entity.result;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2018/1/25.
 */

public class My_Demend_Details_Result implements Serializable{

    /**
     * id : 1
     * name : 家居
     * categoryId : 3
     * categoryName : 家居
     * num : 3
     * value : 555500
     * valueStr : 5555
     * area : 110101
     * areaName : 北京市北京市东城区
     * userId : 54
     * createTime : 1516762822000
     * updateTime : 1516762822000
     * isDelete : false
     * assetId : null
     * type : 1
     * code : null
     */

    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private int num;
    private int value;
    private String valueStr;
    private String area;
    private String areaName;
    private int userId;
    private long createTime;
    private long updateTime;
    private boolean isDelete;
    private Long assetId;
    private int type;
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
