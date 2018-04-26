package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/2.
 */

public class My_Bill_Body extends BaseRequest {

    private Integer pageNo;
    private Integer type=null;
    private Integer pageSize;
    private List<Long> cIds;
    private List<Long>tIds;

    public My_Bill_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public List<Long> getcIds() {
        return cIds;
    }

    public List<Long> gettIds() {
        return tIds;
    }

    public void settIds(List<Long> tIds) {
        this.tIds = tIds;
    }

    public void setcIds(List<Long> cIds) {
        this.cIds = cIds;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
