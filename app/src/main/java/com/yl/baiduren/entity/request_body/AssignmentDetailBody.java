package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2018/1/10.
 */

public class AssignmentDetailBody extends BaseRequest {

    private Long claimsId;//债权Id;

    public Long getClaimsId() {
        return claimsId;
    }

    public void setClaimsId(Long claimsId) {
        this.claimsId = claimsId;
    }

    public AssignmentDetailBody(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
