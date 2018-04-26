package com.yl.baiduren.entity.result;

import com.yl.baiduren.data.BaseRequest;

import java.io.Serializable;

/**
 * Created by Android_apple on 2017/12/27.
 */

public class DebtPresonEntity extends BaseRequest implements Serializable{

    //债事人/企业Id
    private Integer id;
     //债事人类型 1企业；2自然人

    private Integer type;
    /**
     * 债事企业或者债事自然人名称（企业全称或者自然人姓名）
     */
    private String name;
    /**
     *
     * 三证合一代码
     */
    private String companyCode;
    /**
     * 债事企业或者债事自然人所属地域
     */
    private String area;
    /**
     * 债事企业或者债事自然人所属地域
     */
    private String areaStr;
    /**
     * 企业所属行业
     */
    private String companyCategory;
    /**
     * 企业上游行业
     */
    private String companyUpCategory;
    /**
     * 企业下游行业
     */
    private String companyDownCategory;
    /**
     * 企业法人姓名
     */
    private String legalName;
    /**
     * 企业法人或者债事自然人身份证号
     */
    private String idCode;
    /**
     * 企业法人或者债事自然人联系方式
     */
    private String phoneNumber;
    /**
     * 企业法人所属地
     */
    private String legalArea;
    /**
     * 企业法人所属地
     */
    private String legalAreaStr;
    /**
     * 企业法人或者债事自然人联系地址
     */
    private String address;
    /**
     * 证明材料图片地址
     */
    private String img;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改时间
     */
    private Long updateTime;
    /**
     * 是否删除
     */
    private Boolean isDelete;
    /**
     * 用户ID
     */
    private Long userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

    public String getCompanyUpCategory() {
        return companyUpCategory;
    }

    public void setCompanyUpCategory(String companyUpCategory) {
        this.companyUpCategory = companyUpCategory;
    }

    public String getCompanyDownCategory() {
        return companyDownCategory;
    }

    public void setCompanyDownCategory(String companyDownCategory) {
        this.companyDownCategory = companyDownCategory;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
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

    public String getLegalArea() {
        return legalArea;
    }

    public void setLegalArea(String legalArea) {
        this.legalArea = legalArea;
    }

    public String getLegalAreaStr() {
        return legalAreaStr;
    }

    public void setLegalAreaStr(String legalAreaStr) {
        this.legalAreaStr = legalAreaStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DebtPresonEntity(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
