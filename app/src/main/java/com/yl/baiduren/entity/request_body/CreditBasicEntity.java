package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Administrator on 2018/3/30.
 */

public class CreditBasicEntity extends BaseRequest {

    private int type;//1大众 2基础 3深度

    public CreditBasicEntity(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
