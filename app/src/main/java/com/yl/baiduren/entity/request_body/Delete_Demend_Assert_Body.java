package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2017/12/27.
 */

public class Delete_Demend_Assert_Body extends BaseRequest{
    private  Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(Long debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    private Long debtRelationId;

    public Delete_Demend_Assert_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
