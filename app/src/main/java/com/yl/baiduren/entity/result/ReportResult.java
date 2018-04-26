package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ReportResult {


    private List<Report> dataList;

    public static class Report{
        private int id;
        private String searchName;
        private Long  createTime;
        private int type;//1.征信报告基础(大众)版 2.征信报告标准版 3征信报告深度版
        private int status;//1.新建；2.已支付；3.已确认
        private boolean isSupply;//true资产，证信
        private String reportUrl;//报告url

        public String getReportUrl() {
            return reportUrl;
        }

        public void setReportUrl(String reportUrl) {
            this.reportUrl = reportUrl;
        }

        public boolean isSupply() {
            return isSupply;
        }

        public void setSupply(boolean supply) {
            isSupply = supply;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSearchName() {
            return searchName;
        }

        public void setSearchName(String searchName) {
            this.searchName = searchName;
        }

    }

    public List<Report> getDataList() {
        return dataList;
    }

    public void setDataList(List<Report> dataList) {
        this.dataList = dataList;
    }
}
