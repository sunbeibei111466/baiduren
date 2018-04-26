package com.yl.baiduren.base;


/**
 * @author 王锋 on 2017/11/10.
 */

public class BaseEntity<T> {


    private static String SUCCESS_CODE="1";//请求成功

    private String code;
    private String message;
    private T dataResult;
    private BaseRequest baseResponse;
    private String success;

    //判断服务器返回状态
    public boolean isSuccess(){
        return getCode().equals(SUCCESS_CODE);
    }

    public boolean isRe_landing(){
        String RE_LANDING_CODE = "4";
        return getCode().equals(RE_LANDING_CODE);
    }

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

    public T getDataResult() {
        return dataResult;
    }

    public void setDataResult(T dataResult) {
        this.dataResult = dataResult;
    }

    public BaseRequest getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseRequest baseResponse) {
        this.baseResponse = baseResponse;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public BaseEntity() {
    }
}
