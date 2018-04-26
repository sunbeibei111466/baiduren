package com.yl.baiduren.entity.result;

/**
 * Created by sunbeibei on 2017/12/28.
 */

public class Delist_Information_Result {

    /**
     * id : 15
     * fromName : 段鹏飞
     * toName : 李四
     * userName :
     * settleName : 张三公司
     * areaName : 河北省保定市北市区
     * settleMobile :
     * recordTime : 1261141069000
     * commission : 0.17
     * amount : 12312300
     * amountStr : 123123.0
     * createTime : 1513602032000
     * userImage :
     * endTime : null
     * endDays : 0
     * price : 200
     */

    private int id;
    private String fromName;
    private String toName;
    private String userName;
    private String settleName;
    private String areaName;
    private String settleMobile;
    private long recordTime;
    private double commission;
    private int amount;
    private String amountStr;
    private long createTime;
    private String userImage;
    private Object endTime;
    private int endDays;
    private String priceStr;
    private String originalPriceStr;
    private String detail;

    public int getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSettleName() {
        return settleName;
    }

    public void setSettleName(String settleName) {
        this.settleName = settleName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getSettleMobile() {
        return settleMobile;
    }

    public void setSettleMobile(String settleMobile) {
        this.settleMobile = settleMobile;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
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

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public int getEndDays() {
        return endDays;
    }

    public void setEndDays(int endDays) {
        this.endDays = endDays;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getOriginalPriceStr() {
        return originalPriceStr;
    }

    public void setOriginalPriceStr(String originalPriceStr) {
        this.originalPriceStr = originalPriceStr;
    }
}
