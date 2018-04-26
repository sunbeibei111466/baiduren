package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2017/12/27.
 */

public class SponsorEntity extends BaseRequest {

    private int id;
    private int debtRelationId;
    private String name;//担保人名称
    private String area;//担保人地址 code
    private String address;//担保人详细地址
    private String idCode;//担保人身份证号
    private String phoneNumber;//担保人手机号
    private String image;//担保人身份证图片


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(int debtRelationId) {
        this.debtRelationId = debtRelationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
