package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2018/1/26.
 */

public class MyMessageEntity extends BaseRequest {

    private Integer pageNo;//	页数
    private Integer pageSize;//每页数量


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public MyMessageEntity(BaseRequest baseRequest){
        super(baseRequest);
    }

}
