package com.yl.baiduren.entity;

/**
 * Created by sunbeibei on 2017/12/15.
 */

public class Operation_Conditon_Moldel {
    private String industry_name;//产业名称
    private String isbollean;//是否盈亏

    public String getIndustry_name() {
        return industry_name;
    }

    public void setIndustry_name(String industry_name) {
        this.industry_name = industry_name;
    }

    public String getIsbollean() {
        return isbollean;
    }

    public void setIsbollean(String isbollean) {
        this.isbollean = isbollean;
    }

    public String getSpecific_value() {
        return specific_value;
    }

    public void setSpecific_value(String specific_value) {
        this.specific_value = specific_value;
    }

    private String specific_value;//具体价值
}
