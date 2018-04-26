package com.yl.baiduren.entity.result;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2018/3/9.
 */

public class PayEntity extends BaseRequest{

    private Long billId;//订单id
    /**
     * 1.微信支付；2.支付宝支付；3.余额支付；4.银联支付
     */
    private int payType;//

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public PayEntity(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
