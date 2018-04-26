package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2017/12/22.
 */
@Entity
public class LoginSuccess {


    @Id
    private Long login_Id;//表id
    @Property(nameInDb = "accessToken")
    private String accessToken;//请求凭证
    @Property(nameInDb = "id")
    private int id;//用户id
    @Property(nameInDb = "isVip")
    private boolean isVip;//是否是Vip
    @Property(nameInDb = "mobile")
    private String mobile;//手机号
    /**
     * 新注册用户角色  r_new_reg
     * 管理员角色  r_sys_admin
     * 企业子账户用户角色  r_new_enterprise
     * 企业主账户用户角色 r_main_enterprise
     */
    @Property(nameInDb = "roleType")
    private String roleType;//角色类型
    @Property(nameInDb = "userName")
    private String userName;//登录名
    @Property(nameInDb = "userType")
    private Integer userType;//用户类型
    @Generated(hash = 290939444)
    public LoginSuccess(Long login_Id, String accessToken, int id, boolean isVip,
            String mobile, String roleType, String userName, Integer userType) {
        this.login_Id = login_Id;
        this.accessToken = accessToken;
        this.id = id;
        this.isVip = isVip;
        this.mobile = mobile;
        this.roleType = roleType;
        this.userName = userName;
        this.userType = userType;
    }
    @Generated(hash = 344174607)
    public LoginSuccess() {
    }
    public Long getLogin_Id() {
        return this.login_Id;
    }
    public void setLogin_Id(Long login_Id) {
        this.login_Id = login_Id;
    }
    public String getAccessToken() {
        return this.accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
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
