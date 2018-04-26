package com.yl.baiduren.base;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 请求数据基类
 */
@Entity
public class BaseRequest {

    @Id
    private Long bId;
    @Property(nameInDb = "DEVICETOKEN")
    private String deviceToken;
    //"平台类型参数不能为空
    @Property(nameInDb = "PLATFORM")
    private Integer platform; /* 平台 android ios h5 */
    @Property(nameInDb = "UUID")
    private String uuid; /* 手机唯一值 */
    @Property(nameInDb = "ACCESSTOKEN")
    private String accessToken; /*请求凭证*/
    @Property(nameInDb = "TIMESTAMP")
    private Date timestamp;     /*时间戳*/
    @Property(nameInDb = "UID")
    private Long uid; /* 用户UID */
    @Property(nameInDb = "LOGINUSERNAME")
    private String loginUsername;
    @Generated(hash = 1103689665)
    public BaseRequest(Long bId, String deviceToken, Integer platform, String uuid,
            String accessToken, Date timestamp, Long uid, String loginUsername) {
        this.bId = bId;
        this.deviceToken = deviceToken;
        this.platform = platform;
        this.uuid = uuid;
        this.accessToken = accessToken;
        this.timestamp = timestamp;
        this.uid = uid;
        this.loginUsername = loginUsername;
    }
    @Generated(hash = 48962426)
    public BaseRequest() {
    }
    public Long getBId() {
        return this.bId;
    }
    public void setBId(Long bId) {
        this.bId = bId;
    }
    public String getDeviceToken() {
        return this.deviceToken;
    }
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
    public Integer getPlatform() {
        return this.platform;
    }
    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getAccessToken() {
        return this.accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Date getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Long getUid() {
        return this.uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }
    public String getLoginUsername() {
        return this.loginUsername;
    }
    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }


}
