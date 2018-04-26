package com.yl.baiduren.entity;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2017/12/25.
 */

public class DebtRelationDO extends BaseRequest {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DebtRelationDO(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
