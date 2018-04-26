package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/1/9.
 */

public class Delete_Child_Accort extends BaseRequest {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Delete_Child_Accort(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
