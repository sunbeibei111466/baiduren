package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/1/5.
 */

public class Sussful_Exmple_Serch extends BaseRequest{
    private int pageSize;//	每页显示数据数量
    private int pageNo;//	当前页
    private Integer searchType=null;//搜寻类型1.债事总览 2.我的摘牌 3.成功案例
    private String exactParam;//精确查找参数（身份证、名字、备案编号）
    private Long childUserId;

    public Long getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(Long childUserId) {
        this.childUserId = childUserId;
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

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getExactParam() {
        return exactParam;
    }

    public void setExactParam(String exactParam) {
        this.exactParam = exactParam;
    }

    public Sussful_Exmple_Serch(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
