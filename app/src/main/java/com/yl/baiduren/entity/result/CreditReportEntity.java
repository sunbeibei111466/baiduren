package com.yl.baiduren.entity.result;

/**
 * Created by Android_apple on 2018/3/27.
 */

public class CreditReportEntity {

    private String creditReport;
    private String category;
    private Long id;
    private String information;
    private String priceStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getCreditReport() {
        return creditReport;
    }

    public void setCreditReport(String creditReport) {
        this.creditReport = creditReport;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
