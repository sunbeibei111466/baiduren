package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2017/12/26.
 */

public class Debt_PresonEntity extends BaseRequest {

    private Integer type;//债事人类型 1.企业；2.自然人
    private Integer pageSize;//每页条数
    private Integer pageNo;//
    private Long childUserId;//子账号id


    public Debt_PresonEntity(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Long getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(Long childUserId) {
        this.childUserId = childUserId;
    }
}
