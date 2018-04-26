package com.yl.baiduren.entity.result;

/**
 * Created by sunbeibei on 2018/1/3.
 */

public class My_Bill_Details_Result {

    /**
     * id : 72
     * billCode : GRVP201801021605572841269
     * userName : 123456
     * price : 250000
     * priceStr : 2500.0
     * createTime : 1514889311000
     * userImage : null
     * userNickName : I love. you
     * userPhone : 17600223678
     * type : 2
     * settleDays : null
     * vipMonths : 12
     * status : 1
     * debtrelationTimes : null
     * priceAndAmount : null
     */

    private int id;
    private String billCode;
    private String userName;
    private int price;
    private String priceStr;
    private long createTime;
    private String userImage;
    private String userNickName;
    private String userPhone;
    private int type;
    private int settleDays;
    private int vipMonths;
    private int status;
    private int debtrelationTimes;
    private String priceAndAmount;
    private Long debtrelationId;
    private String debtRelationCode;
    private String mobile;
    private String searchName;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getDebtrelationId() {
        return debtrelationId;
    }

    public String getDebtRelationCode() {
        return debtRelationCode;
    }

    public void setDebtRelationCode(String debtRelationCode) {
        this.debtRelationCode = debtRelationCode;
    }

    public void setDebtrelationId(Long debtrelationId) {
        this.debtrelationId = debtrelationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSettleDays() {
        return settleDays;
    }

    public void setSettleDays(int settleDays) {
        this.settleDays = settleDays;
    }

    public int getVipMonths() {
        return vipMonths;
    }

    public void setVipMonths(int vipMonths) {
        this.vipMonths = vipMonths;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDebtrelationTimes() {
        return debtrelationTimes;
    }

    public void setDebtrelationTimes(int debtrelationTimes) {
        this.debtrelationTimes = debtrelationTimes;
    }

    public String getPriceAndAmount() {
        return priceAndAmount;
    }

    public void setPriceAndAmount(String priceAndAmount) {
        this.priceAndAmount = priceAndAmount;
    }
}
