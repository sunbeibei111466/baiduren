package com.yl.baiduren.entity;

/**
 * Created by sunbeibei on 2017/12/11.
 * 债事人详情
 */

public class Debt_Details_Item_Moldel {
    private String name;
    private String money;
    private  String area;
    private  String beizhu;

    public int getTu() {
        return tu;
    }

    public void setTu(int tu) {
        this.tu = tu;
    }

    private  int tu;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
}
