package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2018/1/10.
 */

public class AssigmentDeleteBody extends BaseRequest {

    private Long claimsId;

    public Long getClaimsId() {
        return claimsId;
    }

    public void setClaimsId(Long claimsId) {
        this.claimsId = claimsId;
    }
}
