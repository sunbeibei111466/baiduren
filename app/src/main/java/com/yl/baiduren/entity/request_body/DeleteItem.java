package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2018/1/25.
 */

public class DeleteItem extends BaseRequest {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeleteItem(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public DeleteItem() {
    }
}
