package com.yl.baiduren.entity.result;

/**
 * Created by Android_apple on 2018/2/6.
 */

public class WebEntity {


    /**
     * code : 1
     * message : 登录成功
     * dataResult : null
     * baseResponse : null
     * exceptionCode : null
     * success : true
     */

    private String code;
    private String message;
    private Object dataResult;
    private Object baseResponse;
    private Object exceptionCode;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDataResult() {
        return dataResult;
    }

    public void setDataResult(Object dataResult) {
        this.dataResult = dataResult;
    }

    public Object getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(Object baseResponse) {
        this.baseResponse = baseResponse;
    }

    public Object getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(Object exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
