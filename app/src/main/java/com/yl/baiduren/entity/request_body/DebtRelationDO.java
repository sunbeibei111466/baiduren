package com.yl.baiduren.entity.request_body;

import java.math.BigDecimal;

/**
 * Created by Android_apple on 2017/12/19.
 */

public class DebtRelationDO  {

    private Integer fromId;//债务人ID
    private Integer toId;//债权人ID
    private Long amount;//债事金额（分）
    private String amountStr;//债事金额
    private Integer type;//债事类型 1是货币 2非货币
    private BigDecimal commission;//解债佣金
    private Long recordTime;//发生时间
    private Long endTimeDr;//结束时间
    private String area;
    private Long id;//债事id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
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

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
    }

    public Long getEndTimeDr() {
        return endTimeDr;
    }

    public void setEndTimeDr(Long endTimeDr) {
        this.endTimeDr = endTimeDr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

