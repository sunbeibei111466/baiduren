package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 担保人
 */
@Entity
public class SponsorDO {

    @Id(autoincrement = true)
    private Long id;//条目Id
    @Property(nameInDb = "name")
    private String name;//担保人名称
    @Property(nameInDb = "area")
    private String area;//担保人地址 code
    @Property(nameInDb = "dizhi")
    private String dizhi;//担保人地址 string
    @Property(nameInDb = "address")
    private String address;//担保人详细地址
    @Property(nameInDb = "idCode")
    private String idCode;//担保人身份证号
    @Property(nameInDb = "phoneNumber")
    private String phoneNumber;//担保人手机号
    @Property(nameInDb = "image")
    private String image;//担保人身份证图片
    @Generated(hash = 2018199763)
    public SponsorDO(Long id, String name, String area, String dizhi,
            String address, String idCode, String phoneNumber, String image) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.dizhi = dizhi;
        this.address = address;
        this.idCode = idCode;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }
    @Generated(hash = 387743311)
    public SponsorDO() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getDizhi() {
        return this.dizhi;
    }
    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getIdCode() {
        return this.idCode;
    }
    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }


}
