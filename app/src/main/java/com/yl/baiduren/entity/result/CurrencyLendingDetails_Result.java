package com.yl.baiduren.entity.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/12/26.
 */

public class CurrencyLendingDetails_Result implements Serializable{


    /**
     * id : 23
     * type : 23423
     * principal : 231400
     * principalStr : 2314.0
     * interest : 0.12
     * loanTime : 1508947200000
     * endTime : 1040832000000
     * isPay : null
     * payRecordList : [{"id":12,"payMoney":2134100,"payMoneyStr":"21341.0","payTime":1508947200000,"debtRelationId":44,"moneyLoanId":23,"debtId":2,"userId":28,"createTime":1514261291000,"updateTime":1514261291000,"isDelete":false}]
     * payMoney : 324400
     * payMoneyStr : 3244.0
     * balance : 234200
     * balanceStr : 2342.0
     * debtRelationId : 44
     * userId : 28
     * createTime : 1514261279000
     * updateTime : 1514261279000
     * isDelete : null
     * image :
     */

    private int id;
    private String type;
    private int principal;
    private String principalStr;
    private double interest;
    private long loanTime;
    private long endTime;
    private boolean isPay;
    private int payMoney;
    private String payMoneyStr;
    private int balance;
    private String balanceStr;
    private int debtRelationId;
    private int userId;
    private long createTime;
    private long updateTime;
    private boolean isDelete;
    private String image;
    private List<PayRecordListBean> payRecordList;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrincipal() {
        return principal;
    }

    public void setPrincipal(int principal) {
        this.principal = principal;
    }

    public String getPrincipalStr() {
        return principalStr;
    }

    public void setPrincipalStr(String principalStr) {
        this.principalStr = principalStr;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public long getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(long loanTime) {
        this.loanTime = loanTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public int getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(int payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayMoneyStr() {
        return payMoneyStr;
    }

    public void setPayMoneyStr(String payMoneyStr) {
        this.payMoneyStr = payMoneyStr;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getBalanceStr() {
        return balanceStr;
    }

    public void setBalanceStr(String balanceStr) {
        this.balanceStr = balanceStr;
    }

    public int getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(int debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<PayRecordListBean> getPayRecordList() {
        return payRecordList;
    }

    public void setPayRecordList(List<PayRecordListBean> payRecordList) {
        this.payRecordList = payRecordList;
    }

    public static class PayRecordListBean implements Serializable{
        /**
         * id : 12
         * payMoney : 2134100
         * payMoneyStr : 21341.0
         * payTime : 1508947200000
         * debtRelationId : 44
         * moneyLoanId : 23
         * debtId : 2
         * userId : 28
         * createTime : 1514261291000
         * updateTime : 1514261291000
         * isDelete : false
         */

        private int id;
        private int payMoney;
        private String payMoneyStr;
        private long payTime;
        private int debtRelationId;
        private int moneyLoanId;
        private int debtId;
        private int userId;
        private long createTime;
        private long updateTime;
        private boolean isDelete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(int payMoney) {
            this.payMoney = payMoney;
        }

        public String getPayMoneyStr() {
            return payMoneyStr;
        }

        public void setPayMoneyStr(String payMoneyStr) {
            this.payMoneyStr = payMoneyStr;
        }

        public long getPayTime() {
            return payTime;
        }

        public void setPayTime(long payTime) {
            this.payTime = payTime;
        }

        public int getDebtRelationId() {
            return debtRelationId;
        }

        public void setDebtRelationId(int debtRelationId) {
            this.debtRelationId = debtRelationId;
        }

        public int getMoneyLoanId() {
            return moneyLoanId;
        }

        public void setMoneyLoanId(int moneyLoanId) {
            this.moneyLoanId = moneyLoanId;
        }

        public int getDebtId() {
            return debtId;
        }

        public void setDebtId(int debtId) {
            this.debtId = debtId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isIsDelete() {
            return isDelete;
        }

        public void setIsDelete(boolean isDelete) {
            this.isDelete = isDelete;
        }
    }
}
