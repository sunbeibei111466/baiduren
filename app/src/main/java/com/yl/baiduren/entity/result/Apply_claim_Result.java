package com.yl.baiduren.entity.result;

/**
 * Created by sunbeibei on 2018/1/12.
 */

public class Apply_claim_Result {

    /**
     * id : 1
     * type : 1
     * userId : 27
     * status : 1
     * createTime : null
     * updateTime : null
     */

    private int id;
    private int type;
    private int userId;
    private int status;
    private Object createTime;
    private Object updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }
}
