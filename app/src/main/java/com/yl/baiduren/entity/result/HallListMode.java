package com.yl.baiduren.entity.result;

/**
 * Created by Android_apple on 2018/1/10.
 */

public class HallListMode {


    private Long claimsId;//债权ID
    private String enterpriseName;//公司名字
    private String profile;//简介
    private String imgUrl;//图片地址
    private String areaStr;//所属地区

    public Long getClaimsId() {
        return claimsId;
    }

    public void setClaimsId(Long claimsId) {
        this.claimsId = claimsId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }
}
