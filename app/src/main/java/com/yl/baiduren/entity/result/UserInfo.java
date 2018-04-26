package com.yl.baiduren.entity.result;

/**
 * Created by Android_apple on 2017/12/22.
 */

public class UserInfo {


    /**
     * userType : 0
     * isVip : false
     * mobile : 17600223674
     * idCard : null
     * nickName : 123456
     * image : null
     * burse : null
     * recordNumber : null
     * address : 朝阳区
     * areaStr : 吉林省白山市临江市
     */

    private int userType;
    private boolean isVip;
    private String mobile;
    private Object idCard;
    private String nickName;
    private Object image;
    private Object burse;
    private Object recordNumber;
    private String address;
    private String areaStr;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getIdCard() {
        return idCard;
    }

    public void setIdCard(Object idCard) {
        this.idCard = idCard;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getBurse() {
        return burse;
    }

    public void setBurse(Object burse) {
        this.burse = burse;
    }

    public Object getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(Object recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }
}
