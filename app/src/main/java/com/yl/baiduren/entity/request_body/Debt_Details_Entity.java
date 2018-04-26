package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2017/12/22.
 */

public class Debt_Details_Entity extends BaseRequest {

    private Integer debtrelationId;
    private Long id;
    public Long debtRelationId;
    public Long userId;


    public Debt_Details_Entity(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDebtrelationId() {
        return debtrelationId;
    }

    public void setDebtrelationId(Integer debtrelationId) {
        this.debtrelationId = debtrelationId;
    }

    public Long getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(Long debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
