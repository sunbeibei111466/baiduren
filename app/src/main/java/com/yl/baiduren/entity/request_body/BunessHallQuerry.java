package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2018/1/2.
 */

public class BunessHallQuerry extends BaseRequest {

    private int pageSize;//	每页显示数据数量
    private int pageNo;//	当前页
    private boolean isSettled;//是否已摘牌（true 为已摘牌，false为未摘牌）
    private String exactParam;//精确查找参数（身份证、名字、备案编号）
    private int searchType;

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
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

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    public String getExactParam() {
        return exactParam;
    }

    public void setExactParam(String exactParam) {
        this.exactParam = exactParam;
    }
}
