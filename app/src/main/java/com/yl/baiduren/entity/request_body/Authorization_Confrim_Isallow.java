package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/3/29.
 */

public class Authorization_Confrim_Isallow extends BaseRequest {
    private Long id;
    /**
     * 1.确认；2.拒绝
     */
    private String choice;
    /**
     * 回复原因
     */
    private String replyReason;

    public Authorization_Confrim_Isallow(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getReplyReason() {
        return replyReason;
    }

    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason;
    }
}
