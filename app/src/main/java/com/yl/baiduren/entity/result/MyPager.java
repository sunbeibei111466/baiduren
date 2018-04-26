package com.yl.baiduren.entity.result;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2017/12/22.
 */
@Entity
public class MyPager {

    @Id
    private Long id;
    @Property(nameInDb ="userType" )
    private int userType;
    @Property(nameInDb ="isVip" )
    private boolean isVip;
    @Property(nameInDb ="mobile" )
    private String mobile;
    @Property(nameInDb ="idCard" )
    private String idCard;
    @Property(nameInDb ="realName" )
    private String realName;
    @Property(nameInDb ="nickName" )
    private String nickName;
    @Property(nameInDb ="image" )
    private String image;
    @Property(nameInDb ="burse" )
    private String burse;
    @Property(nameInDb ="recordNumber" )
    private String recordNumber;
    @Property(nameInDb ="address" )
    private String address; //详细
    @Property(nameInDb ="areaStr" )
    private String areaStr;//所属地区 string
    @Property(nameInDb = "standbyMobile")
    private String standbyMobile;
    @Property(nameInDb ="area" )
    private String area;//所属地区编码
    @Property(nameInDb ="roleType" )
    private String roleType;
    /** 登录名 */
    @Property(nameInDb = "userName")
    private String userName;
    @Generated(hash = 601225540)
    public MyPager(Long id, int userType, boolean isVip, String mobile,
            String idCard, String realName, String nickName, String image,
            String burse, String recordNumber, String address, String areaStr,
            String standbyMobile, String area, String roleType, String userName) {
        this.id = id;
        this.userType = userType;
        this.isVip = isVip;
        this.mobile = mobile;
        this.idCard = idCard;
        this.realName = realName;
        this.nickName = nickName;
        this.image = image;
        this.burse = burse;
        this.recordNumber = recordNumber;
        this.address = address;
        this.areaStr = areaStr;
        this.standbyMobile = standbyMobile;
        this.area = area;
        this.roleType = roleType;
        this.userName = userName;
    }
    @Generated(hash = 367296771)
    public MyPager() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUserType() {
        return this.userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
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
    public String getIdCard() {
        return this.idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getRealName() {
        return this.realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getBurse() {
        return this.burse;
    }
    public void setBurse(String burse) {
        this.burse = burse;
    }
    public String getRecordNumber() {
        return this.recordNumber;
    }
    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAreaStr() {
        return this.areaStr;
    }
    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }
    public String getStandbyMobile() {
        return this.standbyMobile;
    }
    public void setStandbyMobile(String standbyMobile) {
        this.standbyMobile = standbyMobile;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
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
   


}
