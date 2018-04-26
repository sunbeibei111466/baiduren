package com.yl.baiduren.entity.request_body;


import com.yl.baiduren.data.BaseRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sunbeibei on 2018/1/4.
 */

public class Sussful_Exmple_Body extends BaseRequest {
    private int  pageSize;
    private int pageNo;
    private Long amount=null;
    private Long minAmount=null;//金额下限
    private Long maxAmount=null;//金额上限
    private Long minCreateTime=null;//债事创建时间最小值
    private Long maxCreateTime=null;//债事创建时间最大值
    private Long minRecordTime=null;//债事发生时间最小值
    private Long maxRecordTime=null;//债事发生时间最大值
    private  String areaCode=null;
    private  Integer assetCategoryId=null;//资产类别
    private  Integer demandCategoryId=null;//需求类别
    private  Integer type=null;//债事金额1货币2非货币
    private BigDecimal commission=null;
    private List<Long> tIds;
    private Long childUserId;//子账号id

    public Long getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(Long childUserId) {
        this.childUserId = childUserId;
    }

    public List<Long> gettIds() {
        return tIds;
    }

    public void settIds(List<Long> tIds) {
        this.tIds = tIds;
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

    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Long getMinCreateTime() {
        return minCreateTime;
    }

    public void setMinCreateTime(Long minCreateTime) {
        this.minCreateTime = minCreateTime;
    }

    public Long getMaxCreateTime() {
        return maxCreateTime;
    }

    public void setMaxCreateTime(Long maxCreateTime) {
        this.maxCreateTime = maxCreateTime;
    }

    public Long getMinRecordTime() {
        return minRecordTime;
    }

    public void setMinRecordTime(Long minRecordTime) {
        this.minRecordTime = minRecordTime;
    }

    public Long getMaxRecordTime() {
        return maxRecordTime;
    }

    public void setMaxRecordTime(Long maxRecordTime) {
        this.maxRecordTime = maxRecordTime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(Integer assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public Integer getDemandCategoryId() {
        return demandCategoryId;
    }

    public void setDemandCategoryId(Integer demandCategoryId) {
        this.demandCategoryId = demandCategoryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Sussful_Exmple_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
