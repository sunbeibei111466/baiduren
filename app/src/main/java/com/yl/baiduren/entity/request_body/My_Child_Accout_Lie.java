package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/1/8.
 */

public class My_Child_Accout_Lie extends BaseRequest {
    private int pageNo;
    private int pageSize;

    public My_Child_Accout_Lie(BaseRequest baseRequest) {
        super(baseRequest);
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
}
