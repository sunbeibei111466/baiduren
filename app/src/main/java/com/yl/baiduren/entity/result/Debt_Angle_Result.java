package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/3/14.
 */

public class Debt_Angle_Result {

    private List<Angle> dataList;

    public static class Angle {

        private String image;
        private String amount;
        private String areaStr;
        private String userName;
        private String totalCount;
        private String completeCount;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAreaStr() {
            return areaStr;
        }

        public void setAreaStr(String areaStr) {
            this.areaStr = areaStr;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getCompleteCount() {
            return completeCount;
        }

        public void setCompleteCount(String completeCount) {
            this.completeCount = completeCount;
        }
    }


    public List<Angle> getDataList() {
        return dataList;
    }

    public void setDataList(List<Angle> dataList) {
        this.dataList = dataList;
    }
}
