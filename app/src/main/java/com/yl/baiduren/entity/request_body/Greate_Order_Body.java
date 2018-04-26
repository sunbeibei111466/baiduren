package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/28.
 */

public class Greate_Order_Body extends BaseRequest {
    private String priceStr = null;
    private Integer type = null;
    private Integer duration = null;
    private Long debtRelationId = null;
    private Integer debtRightId = null;
    private String remark = null;
    private Integer debtrelationTimes = null;
    private Integer vipMonths = null;
    private String searchName=null;//征信查询企业名称
    private Long supplyId=null;//资产处置ID
    private Integer genre=null;//	购买报告类型 1.个人；2.企业
    private String authCode=null	;//	授权码
    private Long creditBillPriceId=null;//信用报告价格表ID



    private List<Integer>billPriceIds=null;


    private Long vipMonthAndPriceId;

    public List<Integer> getBillPriceIds() {
        return billPriceIds;
    }

    public void setBillPriceIds(List<Integer> billPriceIds) {
        this.billPriceIds = billPriceIds;
    }

    public Integer getDebtrelationTimes() {
        return debtrelationTimes;
    }

    public Long getVipMonthAndPriceId() {
        return vipMonthAndPriceId;
    }

    public void setVipMonthAndPriceId(Long vipMonthAndPriceId) {
        this.vipMonthAndPriceId = vipMonthAndPriceId;
    }

    public void setDebtrelationTimes(Integer debtrelationTimes) {
        this.debtrelationTimes = debtrelationTimes;

    }

    public Integer getVipMonths() {
        return vipMonths;
    }

    public void setVipMonths(Integer vipMonths) {
        this.vipMonths = vipMonths;
    }

    public String getPriceStr() {
        return priceStr;

    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(Long debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public Integer getDebtRightId() {
        return debtRightId;
    }

    public void setDebtRightId(Integer debtRightId) {
        this.debtRightId = debtRightId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Greate_Order_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Long getCreditBillPriceId() {
        return creditBillPriceId;
    }

    public void setCreditBillPriceId(Long creditBillPriceId) {
        this.creditBillPriceId = creditBillPriceId;
    }
}
