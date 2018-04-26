package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2017/12/29.
 */

public class DebtPersonSearch extends BaseRequest {

//    private int type;//债事人类型 1.企业；2.自然人
    private int pageSize;//	每页条数
    private int pageNo;//	页码
    private String condition;//条件

//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public DebtPersonSearch(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
