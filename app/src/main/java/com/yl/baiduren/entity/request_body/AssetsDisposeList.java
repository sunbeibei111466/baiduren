package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2018/1/29.
 */

public class AssetsDisposeList extends BaseRequest{

    /**
     * 每页显示数据条数
     */
    private Integer pageSize;
    /**
     * 当前页
     */
    private Integer pageNo;
    /**
     * 类别 1.供需资产；2.备案资产；3.资产处置
     */
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public AssetsDisposeList(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
