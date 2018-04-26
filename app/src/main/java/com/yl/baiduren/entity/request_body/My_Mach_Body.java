package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by sunbeibei on 2018/1/26.
 */

public class My_Mach_Body extends BaseRequest{
    private Long needId;
    private Long supplyId;
    /**
     * 每页显示数据条数
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer pageNo;

    public Long getNeedId() {
        return needId;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public void setNeedId(Long needId) {
        this.needId = needId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
