package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2017/12/25.
 */

public class FeedBackMode extends BaseRequest {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FeedBackMode(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
