package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2017/12/21.
 */
@Entity
public class PayRecord {

    @Id(autoincrement = true)
    private Long id;
    private String payMoneyStr;//结算金额
    private Long payTime;//结算时间

    private Long pId;

    @Generated(hash = 1413031789)
    public PayRecord(Long id, String payMoneyStr, Long payTime, Long pId) {
        this.id = id;
        this.payMoneyStr = payMoneyStr;
        this.payTime = payTime;
        this.pId = pId;
    }

    @Generated(hash = 827572347)
    public PayRecord() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayMoneyStr() {
        return this.payMoneyStr;
    }

    public void setPayMoneyStr(String payMoneyStr) {
        this.payMoneyStr = payMoneyStr;
    }

    public Long getPayTime() {
        return this.payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public Long getPId() {
        return this.pId;
    }

    public void setPId(Long pId) {
        this.pId = pId;
    }
}
