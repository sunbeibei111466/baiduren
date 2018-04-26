package com.yl.baiduren.entity;

import com.yl.baiduren.entity.result.Open_Member_Result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/8.
 */

public class Event_List {

    private List<Open_Member_Result.PersonVipListBean> personMonthsAndPriceList;
    private List<Open_Member_Result.MechanisSixtListBean> mechanisMonthsAndPriceList;
    private String personVipExplain;
    private String mechanisVipExplain;

    public String getPersonVipExplain() {
        return personVipExplain;
    }

    public void setPersonVipExplain(String personVipExplain) {
        this.personVipExplain = personVipExplain;
    }

    public String getMechanisVipExplain() {
        return mechanisVipExplain;
    }

    public void setMechanisVipExplain(String mechanisVipExplain) {
        this.mechanisVipExplain = mechanisVipExplain;
    }

    public List<Open_Member_Result.PersonVipListBean> getPersonMonthsAndPriceList() {
        return personMonthsAndPriceList;
    }

    public void setPersonMonthsAndPriceList(List<Open_Member_Result.PersonVipListBean> personMonthsAndPriceList) {
        this.personMonthsAndPriceList = personMonthsAndPriceList;
    }

    public List<Open_Member_Result.MechanisSixtListBean> getMechanisMonthsAndPriceList() {
        return mechanisMonthsAndPriceList;
    }

    public void setMechanisMonthsAndPriceList(List<Open_Member_Result.MechanisSixtListBean> mechanisMonthsAndPriceList) {
        this.mechanisMonthsAndPriceList = mechanisMonthsAndPriceList;
    }
}
