package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/29.
 */

public class My_Delisting_Result {

    private List<DataListBean> dataList;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * id : 128
         * areaName :
         * commission : 0.15
         * amount : 10000000
         * amountStr : 100000.0
         * createTime : 1514447512000
         * userImage :
         * code :
         * delistingTime : 1514453685000
         * solveTime : null
         */

        private Long id;
        private String areaName;
        private double commission;
        private int amount;
        private String amountStr;
        private long createTime;
        private String userImage;
        private String code;
        private long delistingTime;
        private Long solveTime;
        private Integer endDays;
        private int status;
        private Long endTime;
        /**
         * 解债人姓名
         */
        private String settleName;
        /**
         * 标识（该笔备案是自己的还是子账号的）：1.自己；2：子账号
         */
        private Integer flag;

        public String getSettleName() {
            return settleName;
        }

        public void setSettleName(String settleName) {
            this.settleName = settleName;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }


        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public Integer getEndDays() {
            return endDays;
        }

        public void setEndDays(Integer endDays) {
            this.endDays = endDays;
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getDelistingTime() {
            return delistingTime;
        }

        public void setDelistingTime(long delistingTime) {
            this.delistingTime = delistingTime;
        }

        public Long getSolveTime() {
            return solveTime;
        }

        public void setSolveTime(Long solveTime) {
            this.solveTime = solveTime;
        }
    }
}
