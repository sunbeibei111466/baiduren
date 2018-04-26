package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/4/11.
 */

public class Report_Query_Body extends BaseRequest {
    private String entName;

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public Report_Query_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
