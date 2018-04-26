package com.yl.baiduren.entity.result;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sunbeibei on 2017/12/21.
 */

public class My_Record_Result {


    /**
     * dataList : [{"id":4,"fromName":"陈琦公司","toName":"张三公司","userName":"水电费水电费阿萨德发生的发","settleName":null,"areaName":"北京市北京市东城区","settleMobile":"13511112222","recordTime":1454256000000,"commission":null,"amount":55555,"amountStr":"555.55","createTime":1454256000000,"userImage":""},{"id":8,"fromName":"曹操","toName":"不在链中2","userName":null,"settleName":null,"areaName":"北京市北京市东城区","settleMobile":null,"recordTime":1454256000000,"commission":null,"amount":99999,"amountStr":"999.99","createTime":1454256000000,"userImage":""},{"id":6,"fromName":"张飞","toName":"曹操","userName":null,"settleName":null,"areaName":null,"settleMobile":null,"recordTime":1454256000000,"commission":null,"amount":77777,"amountStr":"777.77","createTime":1454256000000,"userImage":""},{"id":9,"fromName":"张媛","toName":"赵六公司","userName":null,"settleName":null,"areaName":null,"settleMobile":null,"recordTime":1454256000000,"commission":null,"amount":100000,"amountStr":"1000.0","createTime":1454256000000,"userImage":""}]
     * pageSize : 4
     * totalRecord : 5
     * pageIndex : 1
     * totalPage : 2
     * recordIndex : 1
     */

    private int pageSize;
    private int totalRecord;
    private int pageIndex;
    private int totalPage;
    private int recordIndex;
    private List<DataListBean> dataList;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getRecordIndex() {
        return recordIndex;
    }

    public void setRecordIndex(int recordIndex) {
        this.recordIndex = recordIndex;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {


        /**
         * id : 4
         * fromName : 陈琦公司
         * toName : 张三公司
         * userName : 水电费水电费阿萨德发生的发
         * settleName : null
         * areaName : 北京市北京市东城区
         * settleMobile : 13511112222
         * recordTime : 1454256000000
         * commission : null
         * amount : 55555
         * amountStr : 555.55
         * createTime : 1454256000000
         * userImage :
         */
        private Integer endDays;
        private Long id;
        private String fromName;
        private String code;
        private String toName;
        private String userName;
        private String settleName;
        private String areaName;
        private String settleMobile;
        private long recordTime;
        private BigDecimal commission;
        private Long amount;
        private String amountStr;
        private long createTime;
        private String userImage;
        private String progress;
        private Long solveTime;
        private int status;
        public Long getSolveTime() {
            return solveTime;
        }

        public void setSolveTime(Long solveTime) {
            this.solveTime = solveTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }


        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
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

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getToName() {
            return toName;
        }
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public BigDecimal getCommission() {
            return commission;
        }

        public void setCommission(BigDecimal commission) {
            this.commission = commission;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
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
    }
}
