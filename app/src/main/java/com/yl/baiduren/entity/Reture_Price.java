package com.yl.baiduren.entity;

/**
 * Created by sunbeibei on 2018/1/8.
 */

public class Reture_Price {


    private Long vipMonthAndPriceId;
    private Integer type;
    private Integer mouths;
    private String priceStr;

    private Long creditId;
    private String creditName;

    public Reture_Price(Long vipMonthAndPriceId, Integer type, Integer mouths,String priceStr) {
        this.vipMonthAndPriceId = vipMonthAndPriceId;
        this.type = type;
        this.priceStr=priceStr;
        this.mouths=mouths;

    }

    public Reture_Price(Long creditId, String creditName) {
        this.creditId = creditId;
        this.creditName = creditName;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public Long getVipMonthAndPriceId() {
        return vipMonthAndPriceId;
    }

    public void setVipMonthAndPriceId(Long vipMonthAndPriceId) {
        this.vipMonthAndPriceId = vipMonthAndPriceId;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }
    public Integer getMouths() {
        return mouths;
    }

    public void setMouths(Integer mouths) {
        this.mouths = mouths;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
