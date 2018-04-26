package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 产权借贷
 */

@Entity
public class PropertyLoanDO {

    @Id(autoincrement = true)
    private Long id;//条目Id
    @Property(nameInDb = "name")
    private String name;//标的名称
    @Property(nameInDb = "unit")
    private String unit;//计量单位
    @Property(nameInDb = "owner")
    private String owner;//产权归属
    @Property(nameInDb = "rightYear")
    private int rightYear;//产权年限
    @Property(nameInDb = "rightValueStr")
    private String rightValueStr;//评估价值
    @Property(nameInDb = "rightEvaluation")
    private String rightEvaluation;//评估机构
    //专利
    @Property(nameInDb = "patentNo")
    private String patentNo;//专利号
    @Property(nameInDb = "patentYear")
    private int patentYear;//专利年限
    @Property(nameInDb = "patentValueStr")
    private String patentValueStr;//专利评估价值
    @Property(nameInDb = "patentEvaluation")
    private String patentEvaluation;//专利评估机构
    @Property(nameInDb = "returnMoneyStr")
    private Long returnMoneyStr;//已还合计
    @Property(nameInDb = "returnMoney")
    private Long returnMoney;//已还合计
    @Property(nameInDb = "balanceStr")
    private String balanceStr;//未还余额
    @Property(nameInDb = "image")
    private String image;//图片
    @Generated(hash = 1567749419)
    public PropertyLoanDO(Long id, String name, String unit, String owner,
            int rightYear, String rightValueStr, String rightEvaluation,
            String patentNo, int patentYear, String patentValueStr,
            String patentEvaluation, Long returnMoneyStr, Long returnMoney,
            String balanceStr, String image) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.owner = owner;
        this.rightYear = rightYear;
        this.rightValueStr = rightValueStr;
        this.rightEvaluation = rightEvaluation;
        this.patentNo = patentNo;
        this.patentYear = patentYear;
        this.patentValueStr = patentValueStr;
        this.patentEvaluation = patentEvaluation;
        this.returnMoneyStr = returnMoneyStr;
        this.returnMoney = returnMoney;
        this.balanceStr = balanceStr;
        this.image = image;
    }
    @Generated(hash = 1552067057)
    public PropertyLoanDO() {
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
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getOwner() {
        return this.owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public int getRightYear() {
        return this.rightYear;
    }
    public void setRightYear(int rightYear) {
        this.rightYear = rightYear;
    }
    public String getRightValueStr() {
        return this.rightValueStr;
    }
    public void setRightValueStr(String rightValueStr) {
        this.rightValueStr = rightValueStr;
    }
    public String getRightEvaluation() {
        return this.rightEvaluation;
    }
    public void setRightEvaluation(String rightEvaluation) {
        this.rightEvaluation = rightEvaluation;
    }
    public String getPatentNo() {
        return this.patentNo;
    }
    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }
    public int getPatentYear() {
        return this.patentYear;
    }
    public void setPatentYear(int patentYear) {
        this.patentYear = patentYear;
    }
    public String getPatentValueStr() {
        return this.patentValueStr;
    }
    public void setPatentValueStr(String patentValueStr) {
        this.patentValueStr = patentValueStr;
    }
    public String getPatentEvaluation() {
        return this.patentEvaluation;
    }
    public void setPatentEvaluation(String patentEvaluation) {
        this.patentEvaluation = patentEvaluation;
    }
    public Long getReturnMoneyStr() {
        return this.returnMoneyStr;
    }
    public void setReturnMoneyStr(Long returnMoneyStr) {
        this.returnMoneyStr = returnMoneyStr;
    }
    public Long getReturnMoney() {
        return this.returnMoney;
    }
    public void setReturnMoney(Long returnMoney) {
        this.returnMoney = returnMoney;
    }
    public String getBalanceStr() {
        return this.balanceStr;
    }
    public void setBalanceStr(String balanceStr) {
        this.balanceStr = balanceStr;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }



}
