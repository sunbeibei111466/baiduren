package com.yl.baiduren.entity.result;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2017/12/11.
 */
@Entity
public class LoginPassword {


    /**
     * accessToken : ce630b26-b981-4697-8d52-7205efbf4b9c
     * id : 26
     * isVip : false
     * mobile : 13552949045
     * roleType : r_new_reg
     * userName : 王1
     * userType : 0
     */

    @Id
    private Long lID;
    @Property(nameInDb = "accessToken")
    private String accessToken;//请求凭证
    @Property(nameInDb = "id")
    private Integer id;
    @Property(nameInDb = "isVip")
    private boolean isVip;//是否是Vip
    @Property(nameInDb = "mobile")
    private String mobile;//手机号
    @Property(nameInDb = "roleType")
    private String roleType;//角色类型
    @Property(nameInDb = "userName")
    private String userName;//登录名
    @Property(nameInDb = "userType")
    private Integer userType;//用户类型
    @Generated(hash = 317691189)
    public LoginPassword(Long lID, String accessToken, Integer id, boolean isVip,
            String mobile, String roleType, String userName, Integer userType) {
        this.lID = lID;
        this.accessToken = accessToken;
        this.id = id;
        this.isVip = isVip;
        this.mobile = mobile;
        this.roleType = roleType;
        this.userName = userName;
        this.userType = userType;
    }
    @Generated(hash = 1935298464)
    public LoginPassword() {
    }
    public Long getLID() {
        return this.lID;
    }
    public void setLID(Long lID) {
        this.lID = lID;
    }
    public String getAccessToken() {
        return this.accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public boolean getIsVip() {
        return this.isVip;
    }
    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getRoleType() {
        return this.roleType;
    }
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getUserType() {
        return this.userType;
    }
    public void setUserType(Integer userType) {
        this.userType = userType;
    }


}
