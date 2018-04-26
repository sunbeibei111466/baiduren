package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/2.
 */

public class My_Bill_Result {

    private List<DataListBean> dataList;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * id : 8
         * billCode : BA201712271522497226715
         * userName : 王测试
         * price : 88800
         * priceStr : 888.0
         * createTime : 1514889311000
         * userImage : null
         * userNickName : null
         * userPhone : null
         * type : 3
         * settleDays : 20
         * vipMonths : null
         * status : 1
         * debtrelationTimes : null
         * priceAndAmount : 44.4*20
         */

        private Long id;
        private String billCode;
        private String userName;
        private Long price;
        private String priceStr;
        private long createTime;
        private String userImage;
        private String userNickName;
        private String userPhone;
        private int type;
        private int settleDays;
        private Integer vipMonths;
        private int status;
        private Integer debtrelationTimes;
        private String priceAndAmount;
        private Long debtrelationId;
        private String debtRelationCode;
        private String searchName;//征信主体名称
        /**
         * 商城--购买的商品名称
         */
        private String goodsName;
        private Integer isShangCheng;
        private String goodsNum;
        /**
         * 商城的订单ID
         * */
        private Long orderId;
        /**
         * 商城订单的userId
         */
        private Long userId;
        /**
         * 债事订单表的user_id
         */
        private Long bUserId;
        /*商城订单状态*/
        private String statusStr;

        public String getSearchName() {
            return searchName;
        }

        public void setSearchName(String searchName) {
            this.searchName = searchName;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getStatusStr() {
            return statusStr;
        }

        public void setStatusStr(String statusStr) {
            this.statusStr = statusStr;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public Integer getIsShangCheng() {
            return isShangCheng;
        }

        public void setIsShangCheng(Integer isShangCheng) {
            this.isShangCheng = isShangCheng;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getbUserId() {
            return bUserId;
        }

        public void setbUserId(Long bUserId) {
            this.bUserId = bUserId;
        }

        public String getDebtRelationCode() {
            return debtRelationCode;
        }

        public void setDebtRelationCode(String debtRelationCode) {
            this.debtRelationCode = debtRelationCode;
        }

        public Long getId() {
            return id;
        }

        public Long getDebtrelationId() {
            return debtrelationId;
        }

        public void setDebtrelationId(Long debtrelationId) {
            this.debtrelationId = debtrelationId;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getBillCode() {
            return billCode;
        }

        public void setBillCode(String billCode) {
            this.billCode = billCode;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSettleDays() {
            return settleDays;
        }

        public void setSettleDays(int settleDays) {
            this.settleDays = settleDays;
        }

        public Integer getVipMonths() {
            return vipMonths;
        }

        public void setVipMonths(Integer vipMonths) {
            this.vipMonths = vipMonths;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Integer getDebtrelationTimes() {
            return debtrelationTimes;
        }

        public void setDebtrelationTimes(Integer debtrelationTimes) {
            this.debtrelationTimes = debtrelationTimes;
        }

        public String getPriceAndAmount() {
            return priceAndAmount;
        }

        public void setPriceAndAmount(String priceAndAmount) {
            this.priceAndAmount = priceAndAmount;
        }
    }
}
