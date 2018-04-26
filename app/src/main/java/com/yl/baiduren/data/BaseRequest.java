package com.yl.baiduren.data;


import java.util.Date;

/**
 * Created by Android_apple on 2018/3/15.
 */

public class BaseRequest {

    public  String deviceToken ;//推送
    //"平台类型参数不能为空
    public  Integer platform=2; /* 平台 android ios h5 */
    public  String uuid; /* 手机唯一值 */
    public  String accessToken; /*请求凭证*/
    public  Date timestamp;     /*时间戳*/
    public  Long uid; /* 用户UID */
    public  String loginUsername;

    public BaseRequest(){
    }
    public BaseRequest(BaseRequest baseRequest){
        this.platform=baseRequest.platform;
        this.uuid=baseRequest.uuid;
        this.accessToken=baseRequest.accessToken;
        this.uid=baseRequest.uid;
        this.loginUsername=baseRequest.loginUsername;
        this.deviceToken=baseRequest.deviceToken;
    }
}
