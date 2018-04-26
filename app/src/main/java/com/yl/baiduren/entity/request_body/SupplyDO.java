package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2018/1/29.
 */

public class SupplyDO extends BaseRequest {
    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    /**
     * 资产处置所有者身份类型：1.个人；2.企业
     */
    private Integer genre;
    private String companyName;
    private Integer reportId=null;



    public SupplyDO(BaseRequest baseRequest) {
        super(baseRequest);
    }

    /**
     * 供应数量
     */
    private Integer num;
    /**
     * 供应估值(元)
     */
    private String valueStr;
    /**
     * 评估机构
     */
    private String evaluation;
    /**
     * 归属地
     */
    private String area;
    /**
     * 归属地
     */
    private String areaName;
    /**
     * 证明图片
     */
    private String img;
    /**
     * 类别 1.供应；2.备案资产；3.资产处置
     */
    private Integer type;
    /**
     * 资产详细描述
     */
    private String describe;
    private Long supplyId;

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }
}
