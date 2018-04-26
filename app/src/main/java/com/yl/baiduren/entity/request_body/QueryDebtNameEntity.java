package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2017/12/18.
 */

public class QueryDebtNameEntity extends BaseRequest{
    private String condition;


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public QueryDebtNameEntity(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
