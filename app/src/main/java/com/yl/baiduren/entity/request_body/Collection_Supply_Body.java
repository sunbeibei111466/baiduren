package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/1/25.
 */

public class Collection_Supply_Body extends BaseRequest {
    private Long supplyId;
    private Long needId;

    public Collection_Supply_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public Long getNeedId() {
        return needId;
    }
    public void setNeedId(Long needId) {
        this.needId = needId;
    }

}
