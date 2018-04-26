package com.yl.baiduren.entity.result;

/**
 * Created by sunbeibei on 2018/1/10.
 */

public class Recharge_Price_Result {

    /**
     * priceStr : 1
     */

    private String originalPriceStr;//原价
    private String priceStr;//现价
    private String detail;//说明内容

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
