package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/4/11.
 */

public class ZhengXin_Result {
    private String creditReport;
    private List<Category> category;

    public String getCreditReport() {
        return creditReport;
    }

    public void setCreditReport(String creditReport) {
        this.creditReport = creditReport;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category){
        this.category = category;
    }

    public static class Category {
        private String createTime;
        private int creditType;
        private int id;
        private String information;
        private Long originalPrice;
        private String originalPriceStr;
        private Long price;
        private String priceStr;
        private int type;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCreditType() {
            return creditType;
        }

        public void setCreditType(int creditType) {
            this.creditType = creditType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public Long getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Long originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getOriginalPriceStr() {
            return originalPriceStr;
        }

        public void setOriginalPriceStr(String originalPriceStr) {
            this.originalPriceStr = originalPriceStr;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public String getPriceStr() {
            return priceStr;
        }

        public void setPriceStr(String priceStr) {
            this.priceStr = priceStr;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getVipMonths() {
            return vipMonths;
        }

        public void setVipMonths(String vipMonths) {
            this.vipMonths = vipMonths;
        }

        private String updateTime;
        private String vipMonths;

    }
}
