package com.yl.baiduren.entity.result;

/**
 * Created by sunbeibei on 2017/12/25.
 */

public class Updata_Debt_Basic_Information {
  /* fromName : 王测试
         * fromCardNumber : 150105199702264117
            * toName : 王测试公司
         * toCardNumber : null
            * genreName : 混合
         * typeName : 货币
         * amout : null
            * amoutStr : 1000.0
            * recordTime : 2017-08-01 00:00:00
            */

    private String fromName;
    private String fromCardNumber;
    private String toName;
    private String toCardNumber;
    private String genreName;
    private String typeName;
    private String amout;
    private String amoutStr;
    private String recordTime;
    private double commission;
    private int fromId;
    private int toId;
    private String area;
    private String areaStr;
    private String  phoneNumber;

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEndTimeDr() {
        return endTimeDr;
    }

    public void setEndTimeDr(String endTimeDr) {
        this.endTimeDr = endTimeDr;
    }

    private String fromType;
    private String toType;
    private int type;
    private String endTimeDr;
    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromCardNumber() {
        return fromCardNumber;
    }

    public void setFromCardNumber(String fromCardNumber) {
        this.fromCardNumber = fromCardNumber;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToCardNumber() {
        return toCardNumber;
    }

    public void setToCardNumber(String toCardNumber) {
        this.toCardNumber = toCardNumber;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Object getAmout() {
        return amout;
    }

    public void setAmout(String amout) {
        this.amout = amout;
    }

    public String getAmoutStr() {
        return amoutStr;
    }

    public void setAmoutStr(String amoutStr) {
        this.amoutStr = amoutStr;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
