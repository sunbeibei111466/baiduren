package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/25.
 */

public class QuerryEntity extends BaseRequest {

    private int pageSize;//	每页显示数据数量
    private int pageNo;//		当前页
    private Long amount=null;//	固定金额
    private Long minAmount=null;//金额下限
    private Long maxAmount=null;//金额上限
    private Double commission=null;
    private Long minCreateTime=null;//债事创建时间最小值
    private Long maxCreateTime=null;//债事创建时间最大值
    private String areaCode;//所属地域（示例：山东 370000）
    private Integer assetCategoryId=null;//资产类别
    private Integer demandCategoryId=null;//	需求类别
    private String type;//债事金额 1.货币；2.非货币
    private boolean isSettled;//是否已摘牌（true 为已摘牌，false为未摘牌）
    private List<Long> tIds;

    public List<Long> gettIds() {
        return tIds;
    }

    public void settIds(List<Long> tIds) {
        this.tIds = tIds;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(long minAmount) {
        this.minAmount = minAmount;
    }

    public long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public long getMinCreateTime() {
        return minCreateTime;
    }

    public void setMinCreateTime(long minCreateTime) {
        this.minCreateTime = minCreateTime;
    }

    public long getMaxCreateTime() {
        return maxCreateTime;
    }

    public void setMaxCreateTime(long maxCreateTime) {
        this.maxCreateTime = maxCreateTime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(int assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public int getDemandCategoryId() {
        return demandCategoryId;
    }

    public void setDemandCategoryId(int demandCategoryId) {
        this.demandCategoryId = demandCategoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    public QuerryEntity(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
