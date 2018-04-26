package com.yl.baiduren.entity.result;

/**
 * Created by Android_apple on 2018/1/29.
 */

public class ReportPrice {


    /**
     * xyReportPrice : {"type":6,"priceStr":"10","explain":"信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明"}
     * pgReportPrice : {"type":7,"priceStr":"10","explain":"评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明"}
     */

    private XyReportPriceBean xyReportPrice;
    private PgReportPriceBean pgReportPrice;

    public XyReportPriceBean getXyReportPrice() {
        return xyReportPrice;
    }

    public void setXyReportPrice(XyReportPriceBean xyReportPrice) {
        this.xyReportPrice = xyReportPrice;
    }

    public PgReportPriceBean getPgReportPrice() {
        return pgReportPrice;
    }

    public void setPgReportPrice(PgReportPriceBean pgReportPrice) {
        this.pgReportPrice = pgReportPrice;
    }

    public static class XyReportPriceBean {
        /**
         * type : 6
         * priceStr : 10
         * explain : 信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明信用说明
         */

        private int type;
        private String priceStr;
        private String explain;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPriceStr() {
            return priceStr;
        }

        public void setPriceStr(String priceStr) {
            this.priceStr = priceStr;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }
    }

    public static class PgReportPriceBean {
        /**
         * type : 7
         * priceStr : 10
         * explain : 评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明评估说明
         */

        private int type;
        private String priceStr;
        private String explain;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPriceStr() {
            return priceStr;
        }

        public void setPriceStr(String priceStr) {
            this.priceStr = priceStr;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }
    }
}
