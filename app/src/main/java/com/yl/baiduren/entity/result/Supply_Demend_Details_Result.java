package com.yl.baiduren.entity.result;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2018/1/25.
 */

public class Supply_Demend_Details_Result implements Serializable{


    /**
     * id : 1
     * name : 家居
     * categoryId : 3
     * categoryName : 家居
     * num : 3
     * value : 555500
     * valueStr : 5555
     * evaluation : 个人
     * area : 110101
     * areaName : 北京市北京市东城区
     * img :
     * userId : 54
     * createTime : 1516762822000
     * updateTime : 1516762822000
     * isDelete : false
     * assetId : null
     * type : 1
     * describe : null
     * code : null
     */

    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private int num;
    private Long value;
    private String valueStr;
    private String evaluation;
    private String area;
    private String areaName;
    private String img;
    private int userId;
    private long createTime;
    private long updateTime;
    private boolean isDelete;
    private Object assetId;
    private int type;
    private String describe;
    private String code;
    private String companyName;
    private String reportUrl;

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }



    /**
     * 资产处置所有者身份类型：1.个人；2.企业
     */
    private Integer genre;

    /**
     * 是否购买了报告
     */
    private Boolean isBuyCredit;

    /**
     * 报告ID
     */
    private Integer reportId;

    /**
     * 是否认证企业
     */
    private Boolean isAuthCompany;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getBuyCredit() {
        return isBuyCredit;
    }

    public void setBuyCredit(Boolean buyCredit) {
        isBuyCredit = buyCredit;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Boolean getAuthCompany() {
        return isAuthCompany;
    }

    public void setAuthCompany(Boolean authCompany) {
        isAuthCompany = authCompany;
    }

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public Object getAssetId() {
        return assetId;
    }

    public void setAssetId(Object assetId) {
        this.assetId = assetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
