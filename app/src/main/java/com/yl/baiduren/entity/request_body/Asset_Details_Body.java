package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by sunbeibei on 2017/12/26.
 */

public class Asset_Details_Body extends BaseRequest {


    private  Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
