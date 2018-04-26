package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/1/3.
 */

public class My_Bill_Details_Body extends BaseRequest {
    private Long billId=null;
    private String billCode=null;
    private Long id=null  ;

    public My_Bill_Details_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }

    public String getBillCode() {
        return billCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }


    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }



}
